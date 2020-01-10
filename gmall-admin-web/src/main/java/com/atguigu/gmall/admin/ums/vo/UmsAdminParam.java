package com.atguigu.gmall.admin.ums.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**用户登录参数
 * 后台用户管理
 * SpringMVC支持使用 【JSR303】 方式进行校验
 * 1、springboot默认导第三方的校验框架 hibernate-validator
 *
 * 使用JSR303的三大步
 * 1）、给需要校验数据的javaBean上标注校验注解；
 * 2）、告诉SpringBoot，这个需要校验；@Valid 加再需要校验的参数上
 *      springmvc进入方法之前，确定参数值的时候就会进行校验，如果校验出错，直接返回错误，不执行controller代码
 * 3）、如何感知校验成功还是失败；
 *      只需要给开启了校验的javaBean参数后面，紧跟一个BindingResult对象就可以获取到校验结果;
 *         只要有BindingResult，即使校验错了，方法也会执行。我们需要手动处理
 *
 * 统一的异常处理：
 *
 *
 */
@Getter
@Setter
public class UmsAdminParam {
    @Length(min = 6, max = 18, message = "用户名长度必须是6-18位")
    @ApiModelProperty(value = "用户名", required = true)
    @NotEmpty(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码", required = true)
    @NotEmpty(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "用户头像")
    private String icon;

    @ApiModelProperty(value = "邮箱")
    @Email(message = "邮箱格式不合法")
    private String email;

    @NotEmpty(message = "用户昵称不能为空")   //检验 昵称不能为空
    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "备注")
    private String note;
}
