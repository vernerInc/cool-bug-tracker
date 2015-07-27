package ua.com.csltd.common.enums;

/**
 * @author : verner
 * @since : 22.07.2015
 */
public enum Department {
    iFOBS_DEPARTMENT(1, new Object[]{Division.iFOBS
                                    , Division.iFOBSSupport
                                    , Division.iFOBSTest
                                    , Division.iFOBSWeb
                                    , Division.iFOBSB2
                                    , Division.iFOBSWin
                                    , Division.iFOBSMobile
                                    , Division.iFOBSDB
                                    , Division.iFOBSDocsAnalists}),

    B2_DEPARTMENT(2, new Object[]{})

    ;

    public final int id;
    public final Object[] divisions;

    Department(int id, Object[] divisions) {
        this.id = id;
        this.divisions = divisions;
    }
}
