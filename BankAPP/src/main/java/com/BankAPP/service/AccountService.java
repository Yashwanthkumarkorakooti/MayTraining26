package com.BankAPP.service;

import com.BankAPP.dto.*;
import com.BankAPP.enums.*;
import com.BankAPP.exception.ResourceNotFoundException;
import com.BankAPP.mapper.AccountMapper;
import com.BankAPP.model.*;
import com.BankAPP.respository.*;
import com.BankAPP.util.FileUtility;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final BankEmployeeRepository bankEmployeeRepository;
    private final CustomerAccountRepository customerAccountRepository;
    private final AccountMapper accountMapper;
    private final LoanRepository loanRepository;
    private final TransactionRepository transactionRepository;

    private static final String UPLOAD_LOC = "D:/MayTraining/BankReactApp/MaverickBank/public/images";

    public void requestAccount(Type type, String name, MultipartFile aadhar,MultipartFile pan,MultipartFile photo) throws IOException {
        Customer customer = customerRepository.findByUserName(name);

        log.info("Account request initiated by customer: {}", name);


        if(customer == null){
            log.error("Customer not found: {}", name);
            throw new ResourceNotFoundException("Customer Not Found");
        }

        log.info("Customer found. CustomerId={}", customer.getId());

        if(customer.getCustomer_status() == Status.BLOCKED){
            log.warn("Blocked customer attempted account creation. CustomerId={}", customer.getId());
            throw new RuntimeException("Customer Blocked");
        }

        if(customer.getKyc_status() != KycStatus.VERIFIED){
            log.warn("Customer KYC not verified. CustomerId={}", customer.getId());
            throw new RuntimeException("Kyc is not Verified");
        }

        log.info("KYC verification successful for customerId={}", customer.getId());

        FileUtility.validateFile(aadhar);
        FileUtility.validateFile(pan);
        FileUtility.validateFile(photo);

        String folderName = "customer_" + customer.getId() + System.currentTimeMillis() ;
        Path customerFolder = Paths.get(UPLOAD_LOC,folderName);
        Files.createDirectories(customerFolder);

        Files.copy(
                aadhar.getInputStream(),
                customerFolder.resolve("aadhar.jpg"),
                StandardCopyOption.REPLACE_EXISTING);

        Files.copy(
                pan.getInputStream(),
                customerFolder.resolve("pan.jpg"),
                StandardCopyOption.REPLACE_EXISTING
        );

        Files.copy(
                photo.getInputStream(),
                customerFolder.resolve("photo.jpg"),
                StandardCopyOption.REPLACE_EXISTING
        );



        List<Account> existingAccounts = accountRepository.findByCustomer(customer.getId());
        long savingsCount = existingAccounts.stream()
                .filter(a -> a.getType() == Type.SAVINGS && a.getAccountStatus() != AccountStatus.CLOSED).count();
        long currentCount = existingAccounts.stream()
                .filter(a -> a.getType() == Type.CURRENT && a.getAccountStatus() != AccountStatus.CLOSED).count();



        if(type == Type.SAVINGS && savingsCount >= 1){
            throw new RuntimeException("You already have a Savings Account. Only one Savings Account is allowed.");
        }

        if(type == Type.CURRENT && currentCount >= 3){
            throw new RuntimeException("Maximum 3 Current Accounts are allowed.");
        }


        String accountNumber = "MAV" + String.valueOf(System.currentTimeMillis()).substring(5);
        Double balance = (Double) (Math.random() * 5001) + 5000;
        Account account = new Account();

        account.setCustomer(customer);
        account.setAccountNumber(accountNumber);
        account.setBranch(customer.getBranch());
        account.setType(type);
        account.setBalance(balance);
        account.setBranch(customer.getBranch());
        account.setAccountStatus(AccountStatus.PENDING);
        account.setBalance(0.0);
        account.setMinimum_balance(1000.0);
        account.setDocumentpath(folderName);
        accountRepository.save(account);

        CustomerAccount customerAccount = new CustomerAccount();
        customerAccount.setCustomer(customer);
        customerAccount.setAccount(account);
        customerAccount.setOwnership_type(OwnershipType.PRIMARY);
        customerAccount.setRelation_type(RelationType.SELF);

        log.info(
                "Creating account. CustomerId={}, Type={}, Branch={}",
                customer.getId(), type, customer.getBranch().getBranch_name()
        );

        customerAccountRepository.save(customerAccount);

        log.info(
                "Account request created successfully. AccountId={}, AccountNumber={}",
                account.getId(), account.getAccountNumber()
        );

    }

    public ApproveAccountRespDto approveAccount(int accountId, String name) {
        log.info(
                "Account approval request received. Employee={}, AccountId={}",
                name, accountId
        );

        BankEmployee employee = bankEmployeeRepository.findByUsername(name);

        log.info(
                "Employee designation={} attempting approval",
                employee.getDesignation()
        );

        if(employee.getDesignation() != EmployeeDesignation.MANAGER &&
                employee.getDesignation() != EmployeeDesignation.ASSISTANT_MANAGER &&
                employee.getDesignation() != EmployeeDesignation.LOAN_OFFICER ){

            throw new RuntimeException("Not allowed to approve");
        }

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account Not Found"));

        if(account.getAccountStatus() != AccountStatus.PENDING){
            throw new RuntimeException("Account Already Processed");
        }

        account.setBankEmployee(employee);

        account.setAccountNumber(
                generateAccountNumber()
        );
        account.setAccountStatus(AccountStatus.ACTIVE);
        account.setApproved_date(Instant.now());
        account.setOpened_date(Instant.now());

        Branch branch = employee.getBranch();
        account.setBranch(branch);
        Account savedAccount = accountRepository.save(account);

        log.info(
                "Account approved successfully. AccountId={}, ApprovedBy={}",
                accountId, employee.getFullName()
        );

        return accountMapper.mapAccountToDto(savedAccount);

    }
    private String generateAccountNumber(){
        return "ACC" + System.currentTimeMillis();
    }

    public RejectAccountRespDto rejectAccount(int accountId, RejectAccountReqDto dto, String name) {
        log.info(
                "Account rejection request received. AccountId={}, Employee={}",
                accountId, name
        );
        BankEmployee bankEmployee = bankEmployeeRepository.findByUsername(name);
        Account account = accountRepository.findById(accountId)
                .orElseThrow(()-> new ResourceNotFoundException("Account Not Found"));

        if(account.getAccountStatus() != AccountStatus.PENDING){
            throw new RuntimeException("Account is already Proccessed");
        }

        account.setAccountStatus(AccountStatus.REJECTED);
        account.setBankEmployee(bankEmployee);
        account.setSetReason(dto.reason());

        account = accountRepository.save(account);

        log.warn(
                "Account rejected. AccountId={}, Reason={}",
                account.getId(), dto.reason()
        );

        return accountMapper
                .mapRejectDto(account);


    }

    public CloseAccountRespDto closeAccountRequest(int accountId, String name) {
        log.info(
                "Close account request received. AccountId={}, Customer={}",
                accountId, name
        );


        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account Not Exists"));

        log.warn(
                "Account closure denied due to balance. AccountId={}, Balance={}",
                accountId, account.getBalance()
        );

        if(account.getBalance() > 0){
            throw new RuntimeException("Please withdraw balance before closing account");
        }
       boolean activeLoanExists = loanRepository.existActiveLoan(account.getCustomer().getId());
        if (activeLoanExists) {
            throw new RuntimeException("Active loan exists");
        }

        boolean pendingTransaction = transactionRepository.existsPendingTransaction(accountId);
        if (pendingTransaction) {
            throw new RuntimeException("Pending transaction exists");
        }

        account.setAccountStatus(AccountStatus.PENDING);
        account = accountRepository.save(account);

        log.info(
                "Account closure request submitted. AccountId={}",
                accountId
        );

        return accountMapper.mapCloseDto(account);
    }

    public AccountDetailsRespDto getAccountDetails(int accountId, String name) {
        List<CustomerAccount> list = accountRepository.getAccountDetails(accountId);
        if(list.isEmpty()){
            throw new RuntimeException("Account not found");
        }
        return accountMapper
                .mapAccountDetailsDto(
                        list
                );
    }

    public DepositMoneyRespDto depositMoney(String userName, DepositMoneyReqDto dto) {
        log.info(
                "Deposit request. User={}, AccountNumber={}, Amount={}",
                userName, dto.accountNumber(), dto.amount()
        );
        Customer customer = customerRepository.findByUserName(userName);

        Account account = accountRepository.findByAccountNumber(dto.accountNumber())
                        .orElseThrow(() -> new ResourceNotFoundException("Account Not Found"));

        if(account.getCustomer().getId() != customer.getId()){
            throw new RuntimeException(
                    "You can only deposit into your own account"
            );
        }
        if (account.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new RuntimeException("Account not active cannot Deposit money");
        }

        Double currentBalance = account.getBalance() == null ? 0.0 : account.getBalance();
        account.setBalance(currentBalance + dto.amount());
        account = accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setTransaction_reference("TXN" + System.currentTimeMillis());

        transaction.setTransaction_type(TransactionType.DEPOSIT);
        transaction.setAmount(dto.amount());
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setTo_account(account);
        transaction.setRemarks(dto.remarks());

        transaction.setCreated_by_customer(account.getCustomer());
        transaction = transactionRepository.save(transaction);

        log.info(
                "Deposit successful. TransactionRef={}, Amount={}, NewBalance={}",
                transaction.getTransaction_reference(), dto.amount(), account.getBalance()
        );

        return accountMapper.
                mapDepositDto(
                account, transaction
                );
    }

    @Transactional
    public WithdrawMoneyRespDto withdrawMoney(WithdrawMoneyReqDto dto, String userName) {
        log.info(
                "Withdrawal request. User={}, AccountNumber={}, Amount={}",
                userName, dto.accountNumber(), dto.amount()
        );

        Customer customer = customerRepository.findByUserName(userName);

        Account account = accountRepository.findByAccountNumber(dto.accountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Account Not Found"));

        if (account.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new RuntimeException("Account not active");
        }
        Double currentBalance = account.getBalance() == null ? 0.0 : account.getBalance();
        Double remainingBalance = currentBalance - dto.amount();
        if (remainingBalance < account.getMinimum_balance()) {
            throw new RuntimeException("Minimum balance rule violated");
        }


        if (dto.amount() > 50000) {
            throw new RuntimeException("Daily withdrawal limit exceeded");
        }

        account.setBalance(remainingBalance);
        account = accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setTransaction_reference("TXN" +System.currentTimeMillis());
        transaction.setTransaction_type(TransactionType.WITHDRAWAL);
        transaction.setAmount(dto.amount());
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setFrom_account(account);
        transaction.setRemarks(dto.remarks());
        transaction.setCreated_by_customer(account.getCustomer());
        transaction = transactionRepository.save(transaction);

        log.info(
                "Withdrawal successful. TransactionRef={}, Amount={}, RemainingBalance={}",
                transaction.getTransaction_reference(), dto.amount(), account.getBalance()
        );

        return accountMapper
                .mapWithdrawDto(account, transaction);
    }

    public FreezeAccountResponseDto freezeAccount(String employee, int accountId) {
        BankEmployee bankEmployee = bankEmployeeRepository.findByUsername(employee);

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account Not Found"));

        if (bankEmployee.getBranch().getId() != account.getBranch().getId()) {
            throw new RuntimeException(
                    "You can freeze only accounts from your branch"
            );
        }

        if(account.getAccountStatus() != AccountStatus.ACTIVE){
            throw new RuntimeException("Only ACTIVE account can be frozen.");
        }

        AccountStatus oldStatus = account.getAccountStatus();
        account.setAccountStatus(AccountStatus.FROZEN);

        accountRepository.save(account);

        return accountMapper.mapFrozenDto(account,oldStatus);

    }

    public JointAccountRespDto addJointHolder(String employee, int accountId, JointAccountReqDto dto) {
        BankEmployee bankEmployee = bankEmployeeRepository.findByUsername(employee);

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account Not Found."));

        Customer customer = customerRepository.findById(dto.customerId())
                .orElseThrow(() -> new RuntimeException("Customer Not Found"));

        if(account.getAccountStatus() != AccountStatus.ACTIVE){
            throw new RuntimeException(
                    "Joint holder can be added only to ACTIVE accounts"
            );
        }

        if(account.getBranch().getId() != customer.getBranch().getId()){
            throw new RuntimeException("Customer Must belongs to same branch");
        }

        boolean alreadyExists = customerAccountRepository.existsJointAccountHolder(accountId,dto.customerId());
        if(alreadyExists){
            throw new RuntimeException("Customer Already Added");
        }

        CustomerAccount customerAccount = new CustomerAccount();
        customerAccount.setAccount(account);
        customerAccount.setCustomer(customer);
        customerAccount.setOwnership_type(OwnershipType.JOINT);
        customerAccount.setRelation_type(dto.relationType());
        customerAccount.setAccess_level(dto.accessLevel());
        customerAccount.setAdded_by_employee(bankEmployee);
        customerAccount.setIs_active(true);

        customerAccountRepository.save(customerAccount);

        return accountMapper.mapJointAccountDto(customerAccount);
    }

    public FreezeAccountResponseDto activateAccount(String employee, int accountId){

        BankEmployee bankEmployee = bankEmployeeRepository.findByUsername(employee);
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new ResourceNotFoundException("Account Not Found"));

        if (bankEmployee.getBranch().getId() != account.getBranch().getId()) {
            throw new RuntimeException(
                    "You can activate only accounts from your branch"
            );
        }

        if(account.getAccountStatus() != AccountStatus.FROZEN){
            throw new RuntimeException(
                    "Only FROZEN account can be activated"
            );
        }

        AccountStatus oldStatus = account.getAccountStatus();
        account.setAccountStatus(AccountStatus.ACTIVE);
        accountRepository.save(account);
        return accountMapper.mapFrozenDto(account, oldStatus);
    }

    public List<CustomerAccountDto> getCustomerAccounts(String name) {
        Customer customer =
                customerRepository
                        .findByUserName(name);

        return accountRepository.findAccountsByCustomerId(customer.getId())
                .stream()
                .map(account -> new CustomerAccountDto(
                                account.getId(),
                                account.getAccountNumber(),
                                account.getType(),
                                account.getBalance()
                        )
                )
                .toList();
    }

    public Account getById(int accountId){
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account Not Found"));
    }

    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    public void deleteById(int accountId) {
        getById(accountId);
        accountRepository.deleteById(accountId);
    }
}
