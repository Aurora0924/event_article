<script setup>
import { ref } from "vue";

import { userPasswordUpdateService } from "../../api/user";
import { ElMessage } from "element-plus";
import { useTokenStore } from "../../stores/store";
import { useRouter } from "vue-router";
import { useUserInfo } from "../../stores/info";
const userToken = useTokenStore();
const router = useRouter();
const userInfo = useUserInfo();
//定义数据模型
const resetData = ref({
  old_pwd: "",
  new_pwd: "",
  re_pwd: "",
  token: "",
});

//校验密码函数
const checkRePassword = (rule, value, callback) => {
  if (value === "") {
    callback(new Error("请再次确认密码"));
  } else if (value !== resetData.value.new_pwd) {
    callback(new Error("您输入的密码与上面不一致"));
  } else {
    callback();
  }
};
//定义表单校验规则
const rules = {
  old_pwd: [
    {
      required: true,
      message: "请输入原密码",
      trigger: "blur",
    },
    { min: 6, max: 16, message: "请输入密码", trigger: "blur" },
  ],
  new_pwd: [
    {
      required: true,
      message: "请输入密码",
      trigger: "blur",
    },
    { min: 6, max: 16, message: "请输入密码", trigger: "blur" },
  ],
  re_pwd: [
    {
      required: true,
      message: "请确认密码",
      trigger: "blur",
    },
    {
      validator: checkRePassword,
      trigger: "blur",
    }
  ],
};
const userPasswordUpdate = async () => {
  resetData.value.token = userToken.token;
  
  await userPasswordUpdateService(resetData.value).then((res) => {
    ElMessage.success(res.message);
    router.push("/login");
    userToken.removeToken();
    userInfo.removeUserInfo();
  });
};
</script>


<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>重置密码</span>
      </div>
    </template>
    <el-row>
      <el-col :span="12">
        <el-form
          :model="resetData"
          :rules="rules"
          label-width="100px"
          size="large"

        >
          <el-form-item label="原密码:" prop="old_pwd">
            <el-input v-model="resetData.old_pwd"></el-input>
          </el-form-item>
          <el-form-item label="新密码:" prop="new_pwd">
            <el-input v-model="resetData.new_pwd"></el-input>
          </el-form-item>
          <el-form-item label="确认密码:" prop="re_pwd">
            <el-input v-model="resetData.re_pwd"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="userPasswordUpdate()"
              >提交修改</el-button
            >
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </el-card>
</template>