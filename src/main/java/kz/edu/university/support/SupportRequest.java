package kz.edu.university.support;

import kz.edu.university.enums.RequestStatus;
import kz.edu.university.user.Employee;
import kz.edu.university.user.TechSupportSpecialist;

import java.io.Serializable;
import java.time.LocalDateTime;

/** support req with status flow */
public class SupportRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private Employee sender;
    private String description;
    private RequestStatus status;
    private LocalDateTime createdAt;
    private TechSupportSpecialist assignee;

    public SupportRequest() {
    }

    public SupportRequest(Employee sender, String description) {
        this.sender = sender;
        this.description = description;
        this.status = RequestStatus.NEW;
        this.createdAt = LocalDateTime.now();
    }

    public Employee getSender() {
        return sender;
    }

    public String getDescription() {
        return description;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public TechSupportSpecialist getAssignee() {
        return assignee;
    }

    public void assign(TechSupportSpecialist t) {
        this.assignee = t;
    }

    public void markViewed() {
        if (status == RequestStatus.NEW)
            status = RequestStatus.VIEWED;
    }

    public void accept() {
        status = RequestStatus.ACCEPTED;
    }

    public void reject() {
        status = RequestStatus.REJECTED;
    }

    public void markDone() {
        status = RequestStatus.DONE;
    }

    @Override
    public String toString() {
        return "Req{" + status + " from " + (sender != null ? sender.getUsername() : "?")
                + ": " + description + "}";
    }
}
