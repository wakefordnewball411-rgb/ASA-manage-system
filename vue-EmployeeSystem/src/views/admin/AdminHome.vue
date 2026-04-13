<template>
  <el-container class="admin-container">
    <!-- 顶部头部 -->
    <el-header class="header">
      <div class="header-title">后台管理系统</div>
      <div class="header-user">
        <span>欢迎，管理员</span>
        <el-button type="danger" size="small" @click="logout">退出登录</el-button>
      </div>
    </el-header>

    <!-- 主体：侧边栏 + 内容区 -->
    <el-container>
      <!-- 左侧侧边栏 -->
      <el-aside width="220px" class="aside">
        <el-menu
          default-active="1"
          class="sidebar-menu"
          background-color="#2f4050"
          text-color="#fff"
          active-text-color="#ffd04b"
          :router="true"
        >
          <el-menu-item index="/user/home">
            <el-icon><House /></el-icon>
            <span>首页</span>
          </el-menu-item>
          <el-menu-item index="/admin/user">
            <el-icon><User /></el-icon>
            <span>管理员管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/employee">
            <el-icon><Avatar /></el-icon>
            <span>员工管理</span>
          </el-menu-item>
          
        </el-menu>
      </el-aside>

      <!-- 右侧内容区 -->
      <el-main class="main-content">
        <el-card class="home-card">
          <h1>管理员控制台</h1>
          <p>欢迎进入员工管理系统后台，你可以管理员工、用户和系统配置</p>
        </el-card>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { House, User, Avatar } from '@element-plus/icons-vue'

const router = useRouter()

// 退出登录
const logout = () => {
  localStorage.removeItem('token')
  localStorage.removeItem('userInfo')
  ElMessage.success('已退出登录')
  router.push('/login')
}
</script>

<style scoped>
/* 全局容器：纯黑背景 */
.admin-container {
  height: 100vh;
  width: 100vw;
  background: #000;
}

/* 头部样式：黄黑霓虹 */
.header {
  background-color: #000;
  color: #f9e000;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  border-bottom: 2px solid #f9e000;
  box-shadow: 0 0 15px #f9e000;
}

.header-title {
  font-size: 18px;
  font-weight: bold;
  text-shadow: 0 0 8px #f9e000;
}

.header-user {
  display: flex;
  align-items: center;
  gap: 16px;
  color: #f9e000;
}

/* 退出按钮样式 */
.header-user :deep(.el-button) {
  background: #000 !important;
  border: 1px solid #f9e000 !important;
  color: #f9e000 !important;
}
.header-user :deep(.el-button:hover) {
  background: #f9e000 !important;
  color: #000 !important;
  box-shadow: 0 0 8px #f9e000 !important;
}

/* 侧边栏样式：黄黑统一 */
.aside {
  background-color: #000;
  border-right: 2px solid #f9e000;
}

.sidebar-menu {
  --el-menu-bg-color: #000;
  --el-menu-text-color: #f9e000;
  --el-menu-active-color: #f9e000;
  --el-menu-item-hover-fill: #111;
  border-right: none;
  height: 100%;
}

.sidebar-menu :deep(.el-menu-item) {
  color: #f9e000;
}
.sidebar-menu :deep(.el-menu-item.is-active) {
  background: #111 !important;
  border-left: 3px solid #f9e000;
}

/* 内容区样式 */
.main-content {
  background-color: #000;
  padding: 20px;
}

/* 控制台卡片：黄黑霓虹 */
.home-card {
  max-width: 1200px;
  margin: 0 auto;
  background: #000;
  border: 2px solid #f9e000;
  box-shadow: 0 0 15px #f9e000;
}

:deep(.el-card__body) {
  background: #000 !important;
}

.home-card h1 {
  color: #f9e000;
  text-shadow: 0 0 8px #f9e000;
  margin-bottom: 16px;
}

.home-card p {
  color: #f9e000;
  opacity: 0.9;
  font-size: 14px;
}
</style>