package util;

public class WebPaths {
    private static final String PAGE_PREFIX = "/WEB-INF/jsp";
    private static final String PAGE_POSTFIX = ".jsp";

    public static final String HOME = "/home";
    public static final String SIGN_UP = "/reg";
    public static final String SIGN_IN = "/auth";
    public static final String POSTS = "/posts";
    public static final String POST = POSTS + "/*";
    public static final String CREATE_POST = POSTS + "/add";
    public static final String UPDATE_POST = POSTS + "/update";
    public static final String DELETE_POST = POSTS + "/delete";
    public static final String PROFILE = "/profile";
    public static final String IMAGE = "/images/*";

    public static final String HOME_PAGE = PAGE_PREFIX + HOME + PAGE_POSTFIX;
    public static final String SIGN_UP_PAGE = PAGE_PREFIX + SIGN_UP + PAGE_POSTFIX;
    public static final String SIGN_IN_PAGE = PAGE_PREFIX + SIGN_IN + PAGE_POSTFIX;
    public static final String POST_PAGE = PAGE_PREFIX + "/post" + PAGE_POSTFIX;
    public static final String POSTS_PAGE = PAGE_PREFIX + POSTS + PAGE_POSTFIX;
    public static final String CREATE_POST_PAGE = PAGE_PREFIX + "/add" + PAGE_POSTFIX;
    public static final String UPDATE_POST_PAGE = PAGE_PREFIX + "/update_post" + PAGE_POSTFIX;
    public static final String PROFILE_PAGE = PAGE_PREFIX + PROFILE + PAGE_POSTFIX;
}
