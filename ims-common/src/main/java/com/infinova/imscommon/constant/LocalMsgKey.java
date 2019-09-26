package com.infinova.imscommon.constant;

/**
 * @Project:CooperativeProductMS_server
 * @File:LocalMsgKey
 * @Package:com.infinova.pcms.pcmscommon.constant
 * @Description:${todo}
 * @author:ldw
 * @version:V1.0
 * @Date:2019/8/10 17:26
 * @Copyright:2019,ldw@szinfinova.com All Rights Reserved.
 */
public  enum LocalMsgKey {

    system_desc_success("system.desc.success"),//操作成功
    system_desc_error("system.desc.error"),//操作失败
    system_failure_operation("system.failure.operation"),//操作失败！
    system_parameter_invalid("system.parameter.invalid"),//参数异常！
    system_request_invalid("system.request.invalid"),//请求无效！
    system_delete_unallowed("system.delete.unallowed"),//项不可删除

    login_failure_infoError("login.failure.infoError"),//用户名或密码错误！
    login_logout_msg("login.logout.msg"),//退出成功！
    login_failure_other("login.failure.other"),//请先登录！
    user_exists("user.exists"), //用户名或工号已经存在！

    //供应商已存在！
    supplier_name_exists("supplier.name.exists"),

    //角色
    role_name_exists("role.name.exists"),//角色名已存在

    //上传文件提示信息
    upload_exceed_maxSize("upload.exceed.maxSize"), //上传的文件大小超出限制的文件大小！
    upload_filename_exceed_length("upload.filename.exceed.length"), // 上传的文件名最长不超过30个字符！
    upload_excel_error("upload.excel.error"), //请上传规定大小的excel文件！

    //手机号校验
    phone_invalid("phone.invalid"),
    //邮箱校验
    email_invalid("email.invalid");

    private String key;


    public String getKey() {
        return key;
    }

    LocalMsgKey(String key) {
        this.key = key;
    }}

