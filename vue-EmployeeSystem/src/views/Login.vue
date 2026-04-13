<template>
  <div class="login-page">
    <div class="login-box">
      <div class="role-switch">
        <el-button type="primary" size="small" @click="toggleRole">
          {{ roleText }}
        </el-button>
      </div>

      <h3 class="title">员工管理系统</h3>

      <el-form
        ref="loginFormRef"
        :model="form"
        :rules="loginRules"
        class="login-form"
        label-width="0"
      >
        <!-- 账号/姓名输入框 -->
        <el-form-item prop="account">
          <el-input
            v-model="form.account"
            :placeholder="inputPlaceholder"
            clearable
            prefix-icon="User"
          />
        </el-form-item>

        <!-- 密码/部门输入框 -->
        <el-form-item prop="pass">
          <el-input
            v-model="form.pass"
            :type="isAdmin ? 'password' : 'text'"
            :placeholder="isAdmin ? '请输入密码' : '请输入部门'"
            clearable
            :show-password="isAdmin"
            prefix-icon="Lock"
          />
        </el-form-item>

        <el-form-item>
          <el-button
            type="primary"
            class="login-btn"
            @click="doLogin"
            :loading="loading"
          >
            登录
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElForm } from 'element-plus'
import { adminLogin, employeeLogin, type AdminLoginParams, type EmployeeLoginParams } from '@/api/login'

// 状态定义
const isAdmin = ref(true)
const loading = ref(false)
const loginFormRef = ref<InstanceType<typeof ElForm>>()
const router = useRouter()

// 计算属性
const roleText = computed(() => isAdmin.value ? '切换为员工' : '切换为管理员')
const inputPlaceholder = computed(() => isAdmin.value ? '管理员账号' : '员工姓名')

// 表单数据（唯一数据源，杜绝重复）
const form = ref({
  account: '',
  pass: ''
})

// 校验规则
const loginRules = ref({
  account: [
    { required: true, message: '请输入账号', trigger: 'blur' }
  ],
  pass: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ]
})

// 角色切换
const toggleRole = () => {
  isAdmin.value = !isAdmin.value
  // 切换时清空表单
  form.value.account = ''
  form.value.pass = ''
}

// 登录方法（核心修复：杜绝重复字段、传参错位）
const doLogin = async () => {
  if (!loginFormRef.value) return

  try {
    await loginFormRef.value.validate()
  } catch (err) {
    ElMessage.warning('请完善必填信息')
    return
  }

  loading.value = true
  try {
    let res: any

    if (isAdmin.value) {
      const params: AdminLoginParams = {
        username: form.value.account,
        password: form.value.pass
      }
      res = await adminLogin(params)
    } else {
      const params: EmployeeLoginParams = {
        name: form.value.account,
        department: form.value.pass
      }
      res = await employeeLogin(params)
    }

    if (res.code === 200) {
      ElMessage.success('登录成功')
      
      localStorage.setItem('Authorization', res.data.token)
      router.push(isAdmin.value ? '/user/home' : '/employee/home')
    } else {
      ElMessage.error(res.msg || '登录失败')
    }
  } catch (err) {
    ElMessage.error('登录失败，请检查账号或密码')
  } finally {
    loading.value = false
  }
}


</script>

<style scoped>

.login-page {
  width: 100vw;
  height: 100vh;
  background: #000000;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  overflow: hidden;
}

.login-box {
  width: 420px;
  height: 480px;
  background: rgba(0, 0, 0, 0.85);
  border: 2px solid #f9e000;
  border-radius: 0;
  box-shadow: 0 0 15px #f9e000, 0 0 30px rgba(249, 224, 0, 0.3);
  position: relative;
  padding: 28px;
  backdrop-filter: blur(8px);
}

.role-switch {
  position: absolute;
  top: 20px;
  right: 20px;
  z-index: 10;
}

.role-switch :deep(.el-button) {
  background: #000;
  border: 1px solid #f9e000;
  color: #f9e000;
  text-shadow: 0 0 5px #f9e000;
  transition: all 0.3s;
}

.role-switch :deep(.el-button:hover) {
  background: #f9e000;
  border-color: #f9e000;
  color: #000;
  box-shadow: 0 0 10px #f9e000;
}

.title {
  text-align: center;
  margin: 20px 0 40px;
  font-size: 24px;
  font-weight: bold;
  color: #f9e000;
  text-shadow: 0 0 5px #f9e000, 0 0 10px #f9e000;
  letter-spacing: 2px;
}

.login-form {
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 22px;
  position: relative;
  z-index: 5;
}

:deep(.el-input__wrapper) {
  background: rgba(0, 0, 0, 0.7) !important;
  border: 1px solid #f9e000 !important;
  border-radius: 0 !important;
  box-shadow: 0 0 8px #f9e000 !important;
  transition: all 0.3s;
}

:deep(.el-input__wrapper:hover) {
  box-shadow: 0 0 12px #f9e000 !important;
  border-color: #f9e000 !important;
}

:deep(.el-input__inner) {
  color: #f9e000 !important;
  font-size: 15px;
}

:deep(.el-input__prefix) {
  color: #f9e000 !important;
}

.login-btn {
  width: 100%;
  height: 44px;
  font-size: 16px;
  font-weight: bold;
  letter-spacing: 1px;
  background: #f9e000 !important;
  border: none !important;
  color: #000 !important;
  box-shadow: 0 0 15px #f9e000 !important;
  transition: all 0.3s;
}

.login-btn:hover {
  transform: scale(1.02);
  box-shadow: 0 0 25px #f9e000 !important;
}
</style>