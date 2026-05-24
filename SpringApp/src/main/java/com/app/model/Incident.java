package com.app.model;

import com.app.enums.IncidentStatus;
import com.app.enums.IncidentType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Incident {
    private static final Log log = LogFactory.getLog(Incident.class);
    private int id;
    private IncidentType incidentType;
    private String progressDetails;
    private IncidentStatus incidentStatus;
    private int officer_id;

    public Incident(int officer_id,IncidentType incidentType, String progressDetails, IncidentStatus incidentStatus) {
        this.officer_id = officer_id;
        this.incidentType = incidentType;
        this.progressDetails = progressDetails;
        this.incidentStatus = incidentStatus;
    }

    public Incident() {
    }

    public Incident(int id,int officer_id, IncidentType incidentType, String progressDetails, IncidentStatus incidentStatus) {
        this.id = id;
        this.officer_id = officer_id;
        this.incidentType = incidentType;
        this.progressDetails = progressDetails;
        this.incidentStatus = incidentStatus;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public IncidentType getIncidentType() {
        return incidentType;
    }

    public int getOfficer_id() {
        return officer_id;
    }

    public void setOfficer_id(int officer_id) {
        this.officer_id = officer_id;
    }

    public void setIncidentType(IncidentType incidentType) {
        this.incidentType = incidentType;
    }

    public String getProgressDetails() {
        return progressDetails;
    }

    public void setProgressDetails(String progressDetails) {
        this.progressDetails = progressDetails;
    }

    public IncidentStatus getIncidentStatus() {
        return incidentStatus;
    }

    public void setIncidentStatus(IncidentStatus incidentStatus) {
        this.incidentStatus = incidentStatus;
    }

    @Override
    public String toString() {
        return "Incident{" +
                "id=" + id +
                ", incidentType=" + incidentType +
                ", progressDetails='" + progressDetails + '\'' +
                ", incidentStatus=" + incidentStatus +
                '}';
    }
}
