<template>
  <div class="employee-wrapper">
    <!-- 顶部导航 -->
    <div class="top-header">
      <div class="logo">
        <span class="logo-text">ARASAKA</span>
        <span class="sub-title">Employee System</span>
      </div>
      <div class="user-info">
        <el-icon><User /></el-icon>
        <span>荒坂员工</span>
        <el-button type="text" @click="logout">退出</el-button>
      </div>
    </div>

    <div class="main-body">
      <!-- 左侧菜单 -->
      <div class="sidebar">
        <el-menu
          :default-active="activeMenu"
          class="sidebar-menu"
          @select="handleMenuSelect"
        >
          <el-menu-item index="home">
            <el-icon><HomeFilled /></el-icon>
            <span>首页概览</span>
          </el-menu-item>
          <el-menu-item index="profile">
            <el-icon><User /></el-icon>
            <span>个人信息</span>
          </el-menu-item>
          <el-menu-item index="company">
            <el-icon><OfficeBuilding /></el-icon>
            <span>公司介绍</span>
          </el-menu-item>
          <el-menu-item index="news">
            <el-icon><Bell /></el-icon>
            <span>公司新闻</span>
          </el-menu-item>
        </el-menu>
      </div>

      <!-- 主内容区 -->
      <div class="content-box">
        <!-- 1. 首页概览 -->
        <div v-show="activeMenu === 'home'" class="page-container">
          <div class="welcome-card">
            <div class="welcome-text">
              <h2>欢迎回来，{{ userInfo.name || '荒坂员工' }}</h2>
              <p>所属部门：{{ userInfo.department || '技术部' }}</p>
              <p>身份等级：标准雇员 · Arasaka Corporation</p>
            </div>
          </div>

          <div class="info-grid">
            <div class="grid-item">
              <h3>工号</h3>
              <p>{{ userInfo.id ? 'AK-' + userInfo.id : 'AK-0001' }}</p>
            </div>
            <div class="grid-item">
              <h3>账号</h3>
              <p>{{ userInfo.name || 'employee' }}</p>
            </div>
          </div>
        </div>

        <!-- 2. 个人信息（只查看、无Role） -->
        <div v-show="activeMenu === 'profile'" class="page-container">
          <div class="profile-card">
            <h3 class="card-title">个人信息</h3>
            <el-descriptions :column="1" border>
              <el-descriptions-item label="员工ID">
                {{ userInfo.id || '1' }}
              </el-descriptions-item>
              <el-descriptions-item label="姓名">
                {{ userInfo.name || '未设置' }}
              </el-descriptions-item>
              <el-descriptions-item label="性别">
                {{ userInfo.gender || '-' }}
              </el-descriptions-item>
              <el-descriptions-item label="年龄">
                {{ userInfo.age || '-' }}
              </el-descriptions-item>
              <el-descriptions-item label="所属部门">
                {{ userInfo.department || '技术部' }}
              </el-descriptions-item>
            </el-descriptions>
          </div>
        </div>

        <!-- 3. 公司介绍（赛博朋克风格） -->
        <div v-show="activeMenu === 'company'" class="page-container company-page">
          <div class="company-card">
            <div class="cyber-header">
              <h2>Arasaka Corporation</h2>
              <p class="slogan">// PRODUCT.MODULE loading...</p>
            </div>
            
            <!-- 赛博朋克风格主图 -->
            <div class="company-img-container">
              <img
                src="https://img0.baidu.com/it/u=3260213997,2610644831&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=450"
                alt="荒坂集团"
                class="cyber-img"
              />
            </div>

            <div class="company-desc">
              <p>
                <span class="cyber-text">PRODUCT</span>：从金融到安保，从军事到神经科技，荒坂定义未来。
              </p>
              <p>
                <span class="cyber-text">业务覆盖</span>：生物芯片、意识上传技术、私人武装安保、高端武器制造、企业情报网络。
              </p>
            </div>
          </div>
        </div>

        <!-- 4. 公司新闻（赛博朋克2077风格） -->
        <div v-show="activeMenu === 'news'" class="page-container news-page">
          <div class="news-header">
            <h1>荒坂科技</h1>
            <p>
              沉浸探索《赛博朋克》宇宙，从《赛博朋克2077》本篇剧情，到《往日之影》，在这座致命的超级都会夜之城，无数故事等待着你去发现。
            </p>
            
          </div>

          <div class="news-card" v-for="item in newsList" :key="item.id">
            <img :src="item.img" alt="新闻配图" class="news-img">
            <h3>{{ item.title }}</h3>
            <p class="date">{{ item.date }}</p>
            <p class="content">{{ item.content }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { HomeFilled, User, OfficeBuilding, Bell } from '@element-plus/icons-vue'
import request from '@/utils/request'

const router = useRouter()
const activeMenu = ref('home')
// 初始化数据结构，匹配后端Employee实体
const userInfo = ref({
  id: 1,
  name: '荒坂员工',
  department: '技术部',
  age: null,
  gender: null,
  role: null // 不展示
})

//后端获取
onMounted(() => {
  request.get('/employee/info')
    .then(res => {
      if (res?.data) {
        userInfo.value = {
          id: res.data.id,
          name: res.data.name,
          department: res.data.department,
          age: res.data.age,
          gender: res.data.gender,
          role: res.data.role // 接收但不赋值
        }
      }
    })
    .catch(err => {
      console.log('接口请求失败，使用默认数据', err)
    })
})

const handleMenuSelect = (index) => {
  activeMenu.value = index
}

const logout = () => {
  localStorage.removeItem('Authorization')
  
  ElMessage.success('已退出登录')
  router.push('/login')
}

// 赛博朋克风格新闻数据
const newsList = ref([
  {
    id: 1,
    img: "https://img1.baidu.com/it/u=1839225599,3779219338&fm=253&fmt=auto&app=138&f=JPEG?w=600&h=300",
    title: "荒坂发布新一代生物神经芯片 AK-9000",
    date: "2077-04-01",
    content: "全新意识存储芯片正式量产，大幅提升记忆稳定性与脑机接口兼容性，面向企业高管与特殊雇员开放权限。"
  },
  {
    id: 2,
    img: "https://img2.baidu.com/it/u=2943391199,1955532299&fm=253&fmt=auto&app=138&f=JPEG?w=600&h=300",
    title: "夜之城总部安保系统全面升级",
    date: "2077-04-03",
    content: "荒坂塔新增三层无人机防御网络，强化对数据中心、机密实验室与高层区域的保护。"
  }
])
</script>

<style scoped>
/* 全局重置 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: "Microsoft YaHei", sans-serif;
}

/* 主容器：纯黑静态背景 */
.employee-wrapper {
  width: 100vw;
  height: 100vh;
  background: #000000;
  color: #f9e000;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

/* 顶部导航：黄黑赛博边框 */
.top-header {
  height: 60px;
  background: #000;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 24px;
  border-bottom: 2px solid #f9e000;
  box-shadow: 0 0 15px #f9e000;
}

.logo-text {
  font-size: 22px;
  font-weight: bold;
  color: #f9e000;
  text-shadow: 0 0 8px #f9e000;
}

.sub-title {
  font-size: 14px;
  color: #f9e000;
  opacity: 0.8;
  margin-left: 8px;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 12px;
  color: #f9e000;
}

.user-info :deep(.el-button) {
  color: #f9e000 !important;
}

/* 主体布局 */
.main-body {
  display: flex;
  flex: 1;
  overflow: hidden;
}

/* 左侧菜单：黄黑统一风格 */
.sidebar {
  width: 200px;
  background: #000;
  border-right: 2px solid #f9e000;
  flex-shrink: 0;
}

.sidebar-menu {
  height: 100%;
  background: transparent;
  --el-menu-bg-color: #000;
  --el-menu-text-color: #f9e000;
  --el-menu-active-color: #f9e000;
  --el-menu-item-hover-fill: #111;
  --el-menu-border-color: #f9e000;
}

.sidebar-menu :deep(.el-menu-item) {
  color: #f9e000;
}

.sidebar-menu :deep(.el-menu-item.is-active) {
  background: #111 !important;
  border-left: 3px solid #f9e000;
}

/* 内容区 */
.content-box {
  flex: 1;
  padding: 24px;
  overflow-y: auto;
  background: #000;
}

.page-container {
  max-width: 1200px;
  margin: 0 auto;
}

/* 欢迎卡片：黄黑霓虹 */
.welcome-card {
  background: #000;
  padding: 24px;
  border-radius: 0;
  margin-bottom: 20px;
  border: 2px solid #f9e000;
  box-shadow: 0 0 15px #f9e000;
  display: flex;
  align-items: center;
}

.welcome-text h2 {
  margin: 0 0 8px;
  color: #f9e000;
  font-size: 24px;
  text-shadow: 0 0 8px #f9e000;
}

.welcome-text p {
  margin: 4px 0;
  color: #f9e000;
  opacity: 0.9;
}

/* 信息网格 */
.info-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 16px;
}

.grid-item {
  background: #000;
  padding: 20px;
  border-radius: 0;
  border: 2px solid #f9e000;
  box-shadow: 0 0 10px #f9e000;
  text-align: center;
}

.grid-item h3 {
  margin: 0 0 8px;
  font-size: 14px;
  color: #f9e000;
}

.grid-item p {
  margin: 0;
  font-size: 18px;
  color: #f9e000;
  font-weight: bold;
}

/* 个人信息卡片 */
.profile-card {
  background: #000;
  padding: 24px;
  border-radius: 0;
  border: 2px solid #f9e000;
  box-shadow: 0 0 15px #f9e000;
}

.card-title {
  margin-bottom: 16px;
  color: #f9e000;
  font-size: 20px;
  text-shadow: 0 0 8px #f9e000;
}

/* 描述列表样式统一黄黑 */
:deep(.el-descriptions) {
  --el-descriptions-border-color: #f9e000;
  --el-descriptions-label-text-color: #f9e000;
  --el-descriptions-content-text-color: #f9e000;
  background: #000 !important;
}

:deep(.el-descriptions-item) {
  border-color: #f9e000 !important;
}

/* 公司介绍 */
.company-page {
  color: #f9e000;
}

.company-card {
  background: #000;
  padding: 24px;
  border-radius: 0;
  border: 2px solid #f9e000;
  box-shadow: 0 0 20px #f9e000;
}

.cyber-header {
  text-align: center;
  margin-bottom: 20px;
}

.cyber-header h2 {
  color: #f9e000;
  font-size: 28px;
  text-shadow: 0 0 12px #f9e000;
}

.slogan {
  color: #f9e000;
  opacity: 0.7;
  font-family: "Courier New", monospace;
}

.company-img-container {
  text-align: center;
  margin: 20px 0;
}

.cyber-img {
  height: 300px;
  border-radius: 0;
  border: 2px solid #f9e000;
  box-shadow: 0 0 20px #f9e000;
}

.company-desc p {
  line-height: 1.7;
  color: #f9e000;
  margin: 10px 0;
}

.cyber-text {
  color: #f9e000;
  font-weight: bold;
  text-shadow: 0 0 5px #f9e000;
}

/* 公司新闻：完全黄黑统一 */
.news-page {
  color: #f9e000;
}

.news-header {
  background: #000;
  padding: 40px 20px;
  border-radius: 0;
  margin-bottom: 30px;
  text-align: center;
  border: 2px solid #f9e000;
  box-shadow: 0 0 20px #f9e000;
}

.news-header h1 {
  margin: 0 0 15px;
  color: #f9e000;
  font-size: 32px;
  font-weight: bold;
  text-shadow: 0 0 10px #f9e000;
}

.news-header p {
  color: #f9e000;
  opacity: 0.9;
  max-width: 800px;
  margin: 0 auto 20px;
  font-size: 16px;
}

.news-card {
  background: #000;
  padding: 20px;
  border-radius: 0;
  border: 2px solid #f9e000;
  box-shadow: 0 0 10px #f9e000;
  margin-bottom: 15px;
}

.news-img {
  width: 100%;
  height: 220px;
  object-fit: cover;
  border-radius: 0;
  border: 1px solid #f9e000;
  margin-bottom: 10px;
}

.news-card h3 {
  margin: 0 0 6px;
  color: #f9e000;
  text-shadow: 0 0 5px #f9e000;
}

.news-card .date {
  font-size: 12px;
  color: #f9e000;
  opacity: 0.7;
  margin: 0 0 10px;
}

.news-card .content {
  color: #f9e000;
  line-height: 1.6;
  margin: 0;
}
</style>