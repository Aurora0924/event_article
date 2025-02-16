<script setup>
import { Plus, Upload } from "@element-plus/icons-vue";
import { ref } from "vue";
const uploadRef = ref();

//读取用户信息
import { useUserInfo } from "../../stores/info.js";
import { useTokenStore } from "../../stores/store.js";
import { userAvatarUpdateService } from "../../api/user.js";
import { ElMessage } from "element-plus";
const userInfoStore = useUserInfo();
const tokenStore = useTokenStore();
const imgUrl = ref(userInfoStore.info.userPic);

const uploadSuccess = (res) => {
  imgUrl.value = res.data;
};

const updateAvatar = async () => {
  await userAvatarUpdateService(imgUrl.value).then((res) => {
    ElMessage({
      message: "修改成功",
      type: "success",
    });
    
    userInfoStore.info.userPic = imgUrl.value;
  });
};
</script>

<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>更换头像</span>
      </div>
    </template>
    <el-row>
      <el-col :span="12">
        <el-upload
          ref="uploadRef"
          class="avatar-uploader"
          :show-file-list="false"
          :auto-upload="true"
          action="/api/upload"
          name="file"
          :headers="{ Authorization: tokenStore.token }"
          :on-success="uploadSuccess"
        >
          <img v-if="imgUrl" :src="imgUrl" class="avatar" />
          <!-- <img v-else src="../../assets/avatar.jpg" width="278" /> -->
        </el-upload>
        <br />
        <el-button
          type="primary"
          :icon="Plus"
          size="large"
          @click="uploadRef.$el.querySelector('input').click()"
        >
          选择图片
        </el-button>
        <el-button
          type="success"
          :icon="Upload"
          size="large"
          @click="updateAvatar"
        >
          上传头像
        </el-button>
      </el-col>
    </el-row>
  </el-card>
</template>

<style lang="scss" scoped>
.avatar-uploader {
  :deep() {
    .avatar {
      width: 278px;
      height: 278px;
      display: block;
    }

    .el-upload {
      border: 1px dashed var(--el-border-color);
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
      transition: var(--el-transition-duration-fast);
    }

    .el-upload:hover {
      border-color: var(--el-color-primary);
    }

    .el-icon.avatar-uploader-icon {
      font-size: 28px;
      color: #8c939d;
      width: 278px;
      height: 278px;
      text-align: center;
    }
  }
}
</style>