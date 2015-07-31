package ua.com.csltd.common.enums;

/**
 * @author : verner
 * @since : 31.07.2015
 */
public enum BugStatus {
    OPEN(1), IN_PROGRESS(2), ALPHA_TESTING(3), FIXED(4), NOT_REPRODUCIBLE(5), REJECTED(6), NEW(7), TO_BE_DELIVERED(8), AWAITING(8);

    public final long id;

    BugStatus(long id) {
        this.id = id;
    }
}
