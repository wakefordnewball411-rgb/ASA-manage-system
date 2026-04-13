<template>
  <div class="employee-manage">
    <div class="operate-box">
      <el-button type="primary" @click="openAddDialog">新增员工</el-button>
      <el-input v-model="searchName" placeholder="输入ID搜索" style="width: 240px; margin-left: 20px;"></el-input>
  <el-button type="primary" @click="search">搜索</el-button>
    </div>

    <!-- 表格 -->
    <el-table
      v-loading="loading"
      :data="tableData"
      border
      ref="tableRef"
      style="width:100%;margin-top:20px"
    >
      <el-table-column type="selection" width="55"/>
      <el-table-column prop="id" label="ID" width="80"/>
      <el-table-column prop="name" label="姓名"/>
      <el-table-column prop="age" label="年龄" width="80"/>
      <el-table-column prop="gender" label="性别" width="80"/>
      <el-table-column prop="department" label="部门"/>
      <el-table-column label="操作" width="200">
        <template #default="scope">
          <el-button type="success" size="small" @click="openEditDialog(scope.row)">编辑</el-button>
          <el-button type="danger" size="small" @click="deleteOne(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 新增/编辑 弹窗 -->
    <el-dialog v-model="dialogVisible" title="员工信息" width="500px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name"/>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model.number="form.age" placeholder="请输入数字"/>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="form.gender" placeholder="请选择">
            <el-option label="男" value="男"/>
            <el-option label="女" value="女"/>
          </el-select>
        </el-form-item>
        <el-form-item label="部门" prop="department">
          <el-input v-model="form.department"/>
        </el-form-item>
      </el-form>

      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveOrUpdate">确认保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import request from '@/utils/request'
import type { id } from 'element-plus/es/locale/index.mjs'

interface Employee {
  id: number
  name: string
  age: number
  gender: string
  department: string
}

// 响应式数据
const loading = ref(false)
const tableData = ref<Employee[]>([])
const tableRef = ref()
const dialogVisible = ref(false)
const searchName = ref('')
const formRef = ref()
const isEdit = ref(false)

const form = reactive<Employee>({
  id: 0,
  name: '',
  age: 0,
  gender: '',
  department: ''
})

const rules = {
  name: [{ required: true, message: '请输入姓名' }],
  age: [{ required: true, message: '请输入年龄' }],
  gender: [{ required: true, message: '请选择性别' }],
  department: [{ required: true, message: '请输入部门' }]
}

// 获取列表
const getList = async () => {
  loading.value = true
  try {
    // 确保接口路径与后端一致
    const res = await request.get<Employee[]>('/employee/findAll', {
      params: { id: searchName.value }
    })
    tableData.value = res.data
  } finally {
    loading.value = false
  }
}

// 根据ID搜索
const search = async () => {
  if (!searchName.value) {
    // 空的就查全部
    getList()
    return
  }

  loading.value = true
  try {
    const res = await request.get('/employee/findById', {
      params: { id: searchName.value }
    })
    tableData.value = [res.data]
  } catch (err) {
    ElMessage.error('查询失败')
  } finally {
    loading.value = false
  }
}

// 新增
const openAddDialog = () => {
  isEdit.value = false
  Object.assign(form, {
    id: 0,
    name: '',
    age: 0,
    gender: '',
    department: ''
  })
  dialogVisible.value = true
}

// 编辑
const openEditDialog = (row: Employee) => {
  isEdit.value = true
  Object.assign(form, row)
  dialogVisible.value = true
}

// 保存/更新
const saveOrUpdate = async () => {
  await formRef.value.validate()
  try {
    if (isEdit.value) {
      await request.put('/employee/update', form)
    } else {
      await request.post('/employee/add', form)
    }
    ElMessage.success('保存成功')
    dialogVisible.value = false
    getList()
  } catch (e) {
    ElMessage.error('保存失败')
  }
}

// 删除
const deleteOne = async (id: number) => {
  await ElMessageBox.confirm('确认删除？')
  await request.delete(`/employee/delete/${id}`)
  ElMessage.success('删除成功')
  getList()
}

onMounted(() => {
  getList()
})
</script>

<style scoped>

.employee-manage {
  padding: 20px;
  background: #000 !important;
  color: #f9e000;
}

/* 操作栏 */
.operate-box {
  display: flex;
  align-items: center;
  gap: 20px;
}

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

:deep(.el-button--success) {
  background: #000 !important;
  border: 1px solid #f9e000 !important;
  color: #f9e000 !important;
}
:deep(.el-button--success:hover) {
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

:deep(.el-input__wrapper) {
  background: #000 !important;
  border: 1px solid #f9e000 !important;
  box-shadow: 0 0 5px #f9e000 !important;
}
:deep(.el-input__inner) {
  color: #f9e000 !important;
}

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

:deep(.el-checkbox__inner) {
  background: #000 !important;
  border-color: #f9e000 !important;
}
:deep(.el-checkbox__input:checked + .el-checkbox__inner) {
  background: #f9e000 !important;
  border-color: #f9e000 !important;
}

:deep(.el-loading__text) {
  color: #f9e000 !important;
}
:deep(.el-loading-spinner .path) {
  stroke: #f9e000 !important;
}

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

:deep(.el-form-item__label) {
  color: #f9e000 !important;
}

:deep(.el-select__wrapper) {
  background: #000 !important;
  border: 1px solid #f9e000 !important;
  box-shadow: 0 0 5px #f9e000 !important;
}
:deep(.el-select__input) {
  color: #f9e000 !important;
}
:deep(.el-select-dropdown) {
  background: #000 !important;
  border: 1px solid #f9e000 !important;
}
:deep(.el-select-dropdown__item) {
  color: #f9e000 !important;
}
:deep(.el-select-dropdown__item:hover) {
  background: #111 !important;
}
</style>