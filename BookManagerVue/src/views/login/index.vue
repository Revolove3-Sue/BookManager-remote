<template>
    <div class="login-container">
      <div style="display: flex; width: 500px; height: 400px; margin: auto;
                position: absolute;
	              top: 0;
	              left: 0;
	              right: 0;
	              bottom: 0;">
        <el-form
            ref="loginForm"
            :model="loginForm"
            :rules="loginRules"
            class="login-form"
            auto-complete="on"
            label-position="left"
        >
            <!-- 标题 -->
            <div class="title-container">
                <h3 class="title">登录图书管理系统</h3>
            </div>
            <!-- 用户名 -->
            <el-form-item prop="userName">
                <span class="svg-container">
                    <i class="el-icon-a-052" style="font-size: 24px"></i>
                </span>
                <el-input
                    class="yuan"
                    ref="userName"
                    v-model="loginForm.userName"
                    placeholder="请输入用户名"
                    name="userName"
                    type="text"
                    tabindex="1"
                    auto-complete="on"
                />
            </el-form-item>
            <!-- 密码 -->
            <el-form-item prop="password">
                <span class="svg-container">
                    <i class="el-icon-a-051" style="font-size: 24px"></i>
                </span>
                <el-input
                    class="yuan"
                    :key="passwordType"
                    ref="password"
                    v-model="loginForm.password"
                    :type="passwordType"
                    placeholder="请输入密码"
                    name="password"
                    tabindex="2"
                    auto-complete="on"
                    @keyup.enter.native="handleLogin"
                />
                <span class="show-pwd" @click="showPwd">
                    <svg-icon
                        :icon-class="
                            passwordType === 'password' ? 'eye' : 'eye-open'
                        "
                    />
                </span>
            </el-form-item>
            <!-- 权限 -->
            <el-form-item prop="authority">
                <span class="svg-container">
                    <i
                        class="iconfont icon-r-setting"
                        style="font-size: 24px"
                    ></i>
                </span>
                <el-select
                    v-model="loginForm.isAdmin"
                    placeholder="请选择"
                    style="width: 300px"
                >
                    <el-option :key="0" label="读者" :value="0"></el-option>
                    <el-option :key="1" label="管理员" :value="1"></el-option>
                </el-select>
            </el-form-item>

            <!-- 登录按钮 -->
            <div style="height: 40px; margin-bottom: 30px">
                <el-button
                    :disabled="loading"
                    type="primary"
                    style="width: 48%; float: left; font-size: 22px"
                    @click.native.prevent="handleLogin"
                >
                    登录
                </el-button>
                <el-button
                    :disabled="loading"
                    type="success"
                    style="width: 48%; float: right; font-size: 22px"
                    @click.native.prevent="handleRegister"
                >
                    注册
                </el-button>
            </div>
        </el-form>
      </div>
    </div>
</template>

<script>


export default {
    name: "Login",
    data() {
        const validateuserName = (rule, value, callback) => {
            callback();
        };
        const validatePassword = (rule, value, callback) => {
            callback();
        };
        return {
            loginForm: {
                userName: "",
                password: "",
                isAdmin: 0,
            },
            loginRules: {
                userName: [
                    {
                        required: true,
                        trigger: "blur",
                        validator: validateuserName,
                    },
                ],
                password: [
                    {
                        required: true,
                        trigger: "blur",
                        validator: validatePassword,
                    },
                ],
            },
            loading: false,
            passwordType: "password",
            redirect: undefined,
        };
    },
    updated() {
        //
    },
    methods: {
        showPwd() {
            if (this.passwordType === "password") {
                this.passwordType = "";
            } else {
                this.passwordType = "password";
            }
            this.$nextTick(() => {
                this.$refs.password.focus();
            });
        },
        handleLogin() {
            this.$refs.loginForm.validate((valid) => {
                if (valid) {
                    this.loading = true;
                    this.$store
                        .dispatch("user/login", this.loginForm)
                        .then(() => {
                            this.$router.push({ path: "/" });
                            this.loading = false;
                        })
                        .catch((e) => {
                            console.log(e);
                            this.loading = false;
                            if (
                                e.response == undefined ||
                                e.response.data == undefined
                            ) {
                                this.$message({
                                    showClose: true,
                                    message: e,
                                    type: "error",
                                    duration: 5000,
                                });
                            } else {
                                this.$message({
                                    showClose: true,
                                    message: e.response.data,
                                    type: "error",
                                    duration: 5000,
                                });
                            }
                        });
                } else {
                    console.log("不允许提交!");
                    return false;
                }
            });
        },
        handleRegister() {
            console.log("注册按钮");
            this.$router.push({ path: "/register" }); // 进注册页面
        },
    },
    mounted() {
        //
    }
};
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg: #283443;
$light_gray: #222;
$cursor: #222;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
    .login-container .el-input input {
        color: $cursor;
    }
}

/* reset element-ui css */
.login-container {
    .el-input.yuan {
        display: inline-block;
        height: 47px;
        width: 85%;

        input {
            background: transparent;
            border: 0px;
            -webkit-appearance: none;
            border-radius: 0px;
            padding: 12px 5px 12px 15px;
            color: #222;
            height: 47px;
            caret-color: #222;

            &:-webkit-autofill {
                box-shadow: 0 0 0px 1000px $bg inset !important;
                -webkit-text-fill-color: #222 !important;
            }
        }
    }

    .el-input {
        display: inline-block;
        height: 47px;
        width: 100%;

        input {
            background: transparent;
            border: 0px;
            -webkit-appearance: none;
            border-radius: 0px;
            padding: 12px 5px 12px 15px;
            color: #222;
            height: 47px;
            caret-color: #222;

            &:-webkit-autofill {
                box-shadow: 0 0 0px 1000px $bg inset !important;
                -webkit-text-fill-color: #222 !important;
            }
        }
    }

    .el-form-item {
        border: 1px solid rgba(255, 255, 255, 0.1);
        background: rgba(0, 0, 0, 0.1);
        border-radius: 5px;
        color: #454545;
    }
}
</style>

<style lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #222;
$light_gray: #222;

.login-container {
    min-height: 100%;
    width: 100%;
    //background-color: $bg;
    overflow: hidden;
  background-image: url("../../assets/图书馆.jpg");
  background-size: 100%;

    .login-form {
        position: relative;
        width: 520px;
        max-width: 100%;
        //padding: 160px 35px 0;
      padding: 30px;
        margin: 0 auto;
        overflow: hidden;
      background-color: rgba(250,250,250,0.8);

    }

    .tips {
        font-size: 14px;
        color: #222;
        margin-bottom: 10px;

        span {
            &:first-of-type {
                margin-right: 16px;
            }
        }
    }

    .svg-container {
        padding: 6px 5px 6px 15px;
        color: $dark_gray;
        vertical-align: middle;
        width: 30px;
        display: inline-block;

        font-size: 20px;
    }

    .title-container {
        position: relative;

        .title {
            font-size: 26px;
            color: $light_gray;
            margin: 0px auto 40px auto;
            text-align: center;
            font-weight: bold;
        }
    }

    .show-pwd {
        position: absolute;
        right: 10px;
        top: 7px;
        font-size: 16px;
        color: $dark_gray;
        cursor: pointer;
        user-select: none;
    }
}
</style>
