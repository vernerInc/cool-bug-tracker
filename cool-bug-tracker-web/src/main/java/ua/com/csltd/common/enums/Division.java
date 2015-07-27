package ua.com.csltd.common.enums;

/**
 * @author : verner
 * @since : 22.07.2015
 */
public enum Division {
    iFOBS(11)
    , iFOBSSupport(12)
    , iFOBSTest(13)
    , iFOBSWeb(14)
    , iFOBSB2(15)
    , iFOBSWin(16)
    , iFOBSMobile(17)
    , iFOBSDB(18)
    , iFOBSDocsAnalists(19)
    ;

    public final int id;

    Division(int id) {
        this.id = id;
    }
}
