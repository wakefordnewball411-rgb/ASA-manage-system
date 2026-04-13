<template>
  <div style="padding: 20px;">
    <div class="operate-bar">
      <h2>管理员管理</h2>
      <el-button type="primary" @click="openAddDialog">新增管理员</el-button>
    </div>

    <el-table :data="adminList" border style="width: 100%; margin-top: 20px;">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="account" label="登录账号" min-width="160" />
      <el-table-column prop="username" label="姓名" min-width="160" />
      <el-table-column prop="password" label="密码" min-width="160" />
      <el-table-column label="操作" width="180" fixed="right">
        <template #default="{ row }">
          <el-button size="small" type="primary" @click="openEditDialog(row)">编辑</el-button>
          <el-button size="small" type="danger" @click="deleteAdmin(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      v-model="dialogVisible"
      :title="isEdit ? '编辑管理员' : '新增管理员'"
      width="500px"
      destroy-on-close
    >
      <el-form :model="formData" :rules="formRules" ref="formRef" label-width="80px">
        <el-form-item label="登录账号" prop="account">
          <el-input v-model="formData.account" placeholder="请输入登录账号" />
        </el-form-item>
        <el-form-item label="姓名" prop="username">
          <el-input v-model="formData.username" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="formData.password" type="password" placeholder="请输入密码" />
        </el-form-item>
      </el-form>
      <div style="text-align: center; margin-top: 20px;">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import type { FormInstance, FormRules } from 'element-plus'
import request from '@/utils/request'

// 对应后端 User 实体
interface Admin {
  id: number
  account: string
  username: string
  password: string
}

const adminList = ref<Admin[]>([])
const dialogVisible = ref(false)
const isEdit = ref(false)
const formRef = ref<FormInstance>()

const formData = reactive<Admin>({
  id: 0,
  account: '',
  username: '',
  password: ''
})

const formRules: FormRules = {
  account: [{ required: true, message: '请输入登录账号', trigger: 'blur' }],
  username: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

//查询所有：/user/findAll
const getAdminList = () => {
  request.get('/user/findAll').then(res => {
    adminList.value = res.data
  })
}

//新增：/user/add
const addAdmin = () => {
  request.post('/user/add', formData).then(() => {
    ElMessage.success('新增成功')
    dialogVisible.value = false
    getAdminList()
  })
}

//修改：/user/update (PUT 方式，路径完全匹配)
const updateAdmin = () => {
  request.put('/user/update', formData).then(() => { // ✅ 路径100%匹配后端
    ElMessage.success('修改成功')
    dialogVisible.value = false
    getAdminList()
  }).catch(err => {
    ElMessage.error('修改失败：接口未找到')
    console.error(err)
  })
}

// 删除：/user/delete/{id} 
const deleteAdmin = (id: number) => {
  ElMessageBox.confirm('确定删除该管理员？', '提示', { type: 'warning' }).then(() => {
    request.delete(`/user/delete/${id}`).then(() => {
      ElMessage.success('删除成功')
      getAdminList()
    }).catch(err => {
      ElMessage.error('删除失败：接口未找到')
      console.error(err)
    })
  })
}

// 弹窗逻辑
const openAddDialog = () => {
  isEdit.value = false
  dialogVisible.value = true
  Object.assign(formData, { id: 0, account: '', username: '', password: '' })
  formRef.value?.resetFields()
}

const openEditDialog = (row: Admin) => {
  isEdit.value = true
  dialogVisible.value = true
  Object.assign(formData, row)
  formRef.value?.clearValidate()
}

// 提交逻辑
const submitForm = () => {
  formRef.value?.validate(valid => {
    if (valid) {
      isEdit.value ? updateAdmin() : addAdmin()
    }
  })
}


// 初始化
onMounted(() => {
  getAdminList()
})
</script>

<style scoped>
/* 外层容器：黑底 */
div {
  background: #000 !important;
}

/* 操作栏 */
.operate-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: #f9e000;
  text-shadow: 0 0 8px #f9e000;
}

/* 按钮统一赛博朋克黄黑 */
:deep(.el-button--primary) {
  background: #000 !important;
  border: 1px solid #f9e000 !important;
  color: #f9e000 !important;
}
:deep(.el-button--primary:hover) {
  background: #f9e000 !important;
  color: #000 !important;
  box-shadow: 0 0 8px #f9e000 !important;
}

:deep(.el-button--danger) {
  background: #000 !important;
  border: 1px solid #f9e000 !important;
  color: #f9e000 !important;
}
:deep(.el-button--danger:hover) {
  background: #f9e000 !important;
  color: #000 !important;
  box-shadow: 0 0 8px #f9e000 !important;
}

/* 表格：赛博朋克黄黑 */
:deep(.el-table) {
  background: #000 !important;
  border: 2px solid #f9e000 !important;
  --el-table-border-color: #f9e000 !important;
  --el-table-text-color: #f9e000 !important;
  --el-table-header-text-color: #f9e000 !important;
  --el-table-header-bg-color: #000 !important;
}

:deep(.el-table tr) {
  background: #000 !important;
}
:deep(.el-table tr:hover > td) {
  background: #111 !important;
}

:deep(.el-table td) {
  border-color: #f9e000 !important;
  color: #f9e000 !important;
}
:deep(.el-table th) {
  border-color: #f9e000 !important;
  color: #f9e000 !important;
  text-shadow: 0 0 5px #f9e000;
}

/* 弹窗：黄黑霓虹 */
:deep(.el-dialog) {
  background: #000 !important;
  border: 2px solid #f9e000 !important;
  box-shadow: 0 0 20px #f9e000 !important;
}
:deep(.el-dialog__title) {
  color: #f9e000 !important;
  text-shadow: 0 0 8px #f9e000;
}
:deep(.el-dialog__headerbtn .el-icon) {
  color: #f9e000 !important;
}

/* 表单：黄黑 */
:deep(.el-form) {
  color: #f9e000 !important;
}
:deep(.el-form-item__label) {
  color: #f9e000 !important;
}

/* 输入框：黄黑霓虹 */
:deep(.el-input__wrapper) {
  background: #000 !important;
  border: 1px solid #f9e000 !important;
  box-shadow: 0 0 5px #f9e000 !important;
}
:deep(.el-input__inner) {
  color: #f9e000 !important;
}
</style>