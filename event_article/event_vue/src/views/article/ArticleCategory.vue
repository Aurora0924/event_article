<script setup>
import { Edit, Delete } from "@element-plus/icons-vue";
import { ref } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import {
  articleAllService,
  articleAddService,
  articleUpdateService,
  articleDeleteService,
} from "../../api/article";
const categorys = ref([]);
//控制添加分类弹窗
const dialogVisible = ref(false);
//添加分类数据模型
const categoryModel = ref({
  categoryName: "",
  categoryAlias: "",
});
// 清空表单
const clearForm = () => {
  categoryModel.value = {
    categoryName: "",
    categoryAlias: "",
  };
};
//添加分类表单校验
const rules = {
  categoryName: [
    { required: true, message: "请输入分类名称", trigger: "blur" },
  ],
  categoryAlias: [
    { required: true, message: "请输入分类别名", trigger: "blur" },
  ],
};
const title = ref("");
// 获取分类列表
const getAllCategory = async () => {
  await articleAllService().then((res) => {
    categorys.value = res.data;
  });
};
getAllCategory();

//数据回显
const showDialog = (row) => {
  dialogVisible.value = true;
  title.value = "编辑分类";
  categoryModel.value.categoryName = row.categoryName;
  categoryModel.value.categoryAlias = row.categoryAlias;
  categoryModel.value.id = row.id;
};

//添加分类
const addCategory = async () => {
  await articleAddService(categoryModel.value).then((res) => {
    ElMessage.success(res.message);
  });
  //重新刷新列表
  getAllCategory();
  // 关闭弹窗
  dialogVisible.value = false;
};

//添加分类
const updateCategory = async () => {
  await articleUpdateService(categoryModel.value).then((res) => {
    ElMessage.success(res.message);
  });
  //重新刷新列表
  getAllCategory();
  // 关闭弹窗
  dialogVisible.value = false;
};
//删除分类
const deleteCategory = (row) => {
  ElMessageBox.confirm("您确认删除所选中的信息吗?", "温馨提示", {
    type: "warning",
    confirmButtonText: "确认",
    cancelButtonText: "取消",
  })
    .then(async () => {
      //将String类型的id转为Interger类型
      await articleDeleteService(row.id);
      ElMessage({
        type: "success",
        message: "删除成功",
      });
      getAllCategory();
    })
    .catch(() => {
      ElMessage({
        type: "info",
        message: "取消删除",
      });
    });
};
</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>文章分类</span>
        <div class="extra">
          <el-button
            type="primary"
            @click="
              dialogVisible = true;
              title = '添加分类';
              clearForm();
            "
            >添加分类</el-button
          >
        </div>
      </div>
    </template>
    <el-table :data="categorys" style="width: 100%">
      <el-table-column label="序号" width="100" type="index"> </el-table-column>
      <el-table-column label="分类名称" prop="categoryName"></el-table-column>
      <el-table-column label="分类别名" prop="categoryAlias"></el-table-column>
      <el-table-column label="操作" width="200">
        <template #default="scoped">
          <el-button
            :icon="Edit"
            auto-insert-space
            size="small"
            type="primary"
            @click="showDialog(scoped.row)"
            >编辑</el-button
          >
          <el-button
            :icon="Delete"
            auto-insert-space
            size="small"
            type="danger"
            @click="deleteCategory(scoped.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>
    <!-- 添加分类弹窗 -->
    <el-dialog v-model="dialogVisible" :title="title" width="30%">
      <el-form
        :model="categoryModel"
        :rules="rules"
        label-width="100px"
        style="padding-right: 30px"
      >
        <el-form-item label="分类名称" prop="categoryName">
          <el-input
            v-model="categoryModel.categoryName"
            minlength="1"
            maxlength="10"
          ></el-input>
        </el-form-item>
        <el-form-item label="分类别名" prop="categoryAlias">
          <el-input
            v-model="categoryModel.categoryAlias"
            minlength="1"
            maxlength="15"
          ></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button
            type="primary"
            @click="title === '添加分类' ? addCategory() : updateCategory()"
          >
            确认
          </el-button>
        </span>
      </template>
    </el-dialog>
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
</style>