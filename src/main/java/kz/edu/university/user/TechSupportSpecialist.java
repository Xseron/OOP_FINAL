package kz.edu.university.user;

import kz.edu.university.enums.Language;
import kz.edu.university.support.SupportRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** tech support */
public class TechSupportSpecialist extends Employee {
    private static final long serialVersionUID = 1L;

    private List<SupportRequest> assignedRequests = new ArrayList<>();

    public TechSupportSpecialist() {
    }

    public TechSupportSpecialist(int id, String name, String email, String username, String pwd,
            Language lang, double sal, LocalDate hireDt) {
        super(id, name, email, username, pwd, lang, sal, hireDt);
    }

    public List<SupportRequest> getAssignedRequests() {
        return assignedRequests;
    }

    /** see new */
    public List<SupportRequest> viewNewRequests() {
        return assignedRequests.stream()
                .filter(r -> r.getStatus() == kz.edu.university.enums.RequestStatus.NEW)
                .toList();
    }

    public void markViewed(SupportRequest request) {
        request.markViewed();
    }

    public void acceptRequest(SupportRequest request) {
        request.accept();
    }

    public void rejectRequest(SupportRequest request) {
        request.reject();
    }

    public void markDone(SupportRequest request) {
        request.markDone();
    }
}
