<script setup>
import { Edit, Delete } from "@element-plus/icons-vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { QuillEditor } from "@vueup/vue-quill";
import "@vueup/vue-quill/dist/vue-quill.snow.css";
import { useTokenStore } from "../../stores/store";
import { ref } from "vue";
import {
  articleAllService,
  articleDetailService,
  articleAddArticleService,
  articleUpdateArticleService,
  articleDeleteArticleService,
} from "../../api/article";
const token = useTokenStore();
//文章分类数据模型
const categorys = ref([]);
const drawerTitle = ref("");
//用户搜索时选中的分类id
const categoryId = ref("");

//用户搜索时选中的发布状态
const state = ref("");

//文章列表数据模型
const articles = ref([]);

//分页条数据模型
const pageNum = ref(1); //当前页
const total = ref(20); //总条数
const pageSize = ref(10); //每页条数

import { Plus } from "@element-plus/icons-vue";
//控制抽屉是否显示
const visibleDrawer = ref(false);
//添加表单数据模型
const articleModel = ref({
  title: "",
  categoryId: "",
  coverImg: "",
  content: "",
  state: "",
});
//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
  pageSize.value = size;
  getAllArticle();
};
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num;
  getAllArticle();
};
//获取搜索文章列表
const getAllCategory = async () => {
  await articleAllService().then((res) => {
    categorys.value = res.data;
  });
};

// 获取文章列表数据
const getAllArticle = async () => {
  const params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    categoryId: categoryId.value ? categoryId.value : null,
    state: state.value ? state.value : null,
  };
  let res = await articleDetailService(params);
  articles.value = res.data.items;
  total.value = res.data.total;

  // 遍历 articles 为 categoryName 赋值
  articles.value.forEach((article) => {
    const category = categorys.value.find((c) => c.id === article.categoryId);
    if (category) {
      article.categoryName = category.categoryName;
    }
  });
};
//  重置搜索条件
const resetArticle = () => {
  categoryId.value = "";
  state.value = "";
  getAllArticle();
};
//文件上传成功回调
const handleAvatarSuccess = (res) => {
  articleModel.value.coverImg = res.data;
};
//添加文章
const saveArticle = async (state) => {
  articleModel.value.state = state;
  await articleAddArticleService(articleModel.value).then((res) => {
    ElMessage.success(res.message);
    visibleDrawer.value = false;

    getAllArticle();
  });
};

const showDialog = (row) => {
  drawerTitle.value = "修改文章";
  visibleDrawer.value = true;
  articleModel.value.title = row.title;
  articleModel.value.content = row.content;
  articleModel.value.coverImg = row.coverImg;
  articleModel.value.id = row.id;
  articleModel.value.categoryId = row.categoryId;
};

//修改文章
const updateArticle = async (state) => {
  articleModel.value.state = state;
  await articleUpdateArticleService(articleModel.value).then((res) => {
    ElMessage.success(res.message);
    articleModel.value = {};
  });
  getAllArticle();
  visibleDrawer.value = false;
};
//删除文章
const deleteArticle = async (row) => {
  ElMessageBox.confirm("您确认删除所选中的信息吗?", "温馨提示", {
    type: "warning",
    confirmButtonText: "确认",
    cancelButtonText: "取消",
  })
    .then(async () => {
      //将String类型的id转为Interger类型
      await articleDeleteArticleService(row.id);
      ElMessage({
        type: "success",
        message: "删除成功",
      });
      getAllArticle();
    })
    .catch(() => {
      ElMessage({
        type: "info",
        message: "取消删除",
      });
    });
};
getAllCategory();
getAllArticle();
</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>文章管理</span>
        <div class="extra">
          <el-button
            type="primary"
            @click="
              visibleDrawer = true;
              drawerTitle = '添加文章';
            "
            >添加文章</el-button
          >
        </div>
      </div>
    </template>
    <!-- 搜索表单 -->
    <el-form inline>
      <el-form-item label="文章分类：">
        <el-select
          placeholder="请选择"
          v-model="categoryId"
          style="width: 200px"
        >
          <el-option
            v-for="c in categorys"
            :key="c.id"
            :label="c.categoryName"
            :value="c.id"
          >
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="发布状态：">
        <el-select placeholder="请选择" v-model="state" style="width: 200px">
          <el-option label="已发布" value="已发布"></el-option>
          <el-option label="草稿" value="草稿"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getAllArticle">搜索</el-button>
        <el-button @click="resetArticle">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 文章列表 -->
    <el-table :data="articles" style="width: 100%">
      <el-table-column
        label="文章标题"
        width="400"
        prop="title"
      ></el-table-column>
      <el-table-column label="分类" prop="categoryName"></el-table-column>
      <el-table-column label="发表时间" prop="createTime"> </el-table-column>
      <el-table-column label="状态" prop="state"></el-table-column>
      <el-table-column label="操作" width="200">
        <template #default="{ row }">
          <el-button
            :icon="Edit"
            auto-insert-space
            size="small"
            type="primary"
            @click="showDialog(row)"
            >修改</el-button
          >
          <el-button
            :icon="Delete"
            auto-insert-space
            size="small"
            type="danger"
            @click="deleteArticle(row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>
    <!-- 分页条 -->
    <el-pagination
      v-model="pageNum"
      :page-size="pageSize"
      :page-sizes="[3, 5, 10, 15]"
      layout="jumper, total, sizes, prev, pager, next"
      background
      :total="total"
      @size-change="onSizeChange"
      @current-change="onCurrentChange"
      style="margin-top: 20px; justify-content: flex-end"
    />
    <!-- 抽屉 -->
    <el-drawer
      v-model="visibleDrawer"
      :title="drawerTitle"
      direction="rtl"
      size="50%"
    >
      <!-- 添加文章表单 -->
      <el-form :model="articleModel" label-width="100px">
        <el-form-item label="文章标题">
          <el-input
            v-model="articleModel.title"
            placeholder="请输入标题"
          ></el-input>
        </el-form-item>
        <el-form-item label="文章分类">
          <el-select placeholder="请选择" v-model="articleModel.categoryId">
            <el-option
              v-for="c in categorys"
              :key="c.id"
              :label="c.categoryName"
              :value="c.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="文章封面">
          <el-upload
            class="avatar-uploader"
            :auto-upload="true"
            :show-file-list="false"
            action="/api/upload"
            name="file"
            :headers="{ Authorization: token.token }"
            :on-success="handleAvatarSuccess"
          >
            <img
              v-if="articleModel.coverImg"
              :src="articleModel.coverImg"
              class="avatar"
            />
            <el-icon v-else class="avatar-uploader-icon">
              <Plus />
            </el-icon>
          </el-upload>
        </el-form-item>
        <el-form-item label="文章内容">
          <div class="editor">
            <quill-editor
              theme="snow"
              v-model:content="articleModel.content"
              contentType="html"
            >
            </quill-editor>
          </div>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            @click="
              drawerTitle === '添加文章'
                ? saveArticle('已发布')
                : updateArticle('已发布')
            "
            >发布</el-button
          >
          <el-button
            type="info"
            @click="
              drawerTitle === '添加文章'
                ? saveArticle('草稿')
                : updateArticle('草稿')
            "
            >草稿</el-button
          >
        </el-form-item>
      </el-form>
    </el-drawer>
  </el-card>
</template>
<style lang="scss" scoped>
.page-container {
  min-height: 100%;
  box-sizing: border-box;

  .header {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
}
/* 抽屉样式 */
.avatar-uploader {
  :deep() {
    .avatar {
      width: 178px;
      height: 178px;
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
      width: 178px;
      height: 178px;
      text-align: center;
    }
  }
}
.editor {
  width: 100%;
  :deep(.ql-editor) {
    min-height: 200px;
  }
}
</style>