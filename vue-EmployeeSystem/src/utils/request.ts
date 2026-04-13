import axios from 'axios'
import { ElMessage } from 'element-plus'

//创建axios
const service = axios.create({
  baseURL: 'http://localhost:8080',
  timeout:5000
})

//请求拦截器
service.interceptors.request.use(
  config => {
    // 统一用 Authorization token
    const token = localStorage.getItem('Authorization')
    
    if (token && token !== '[object Object]') {
      config.headers['Authorization'] = token
    }

    // 统一设置Content-Type
    config.headers['Content-Type'] = 'application/json;charset=UTF-8'
    return config
  },
  error => {
    console.error('请求错误',error)
    ElMessage.error('请求错误')
    return Promise.reject(error)
  }
)

//响应拦截器
service.interceptors.response.use(
  response => {
    const res = response.data
    if(res.code!==200){
      ElMessage.error(res.msg||'请求失败')
      return Promise.reject(new Error(res.msg||'请求失败'))
    }
    return res
  },
  error => {
    console.error('响应报错',error)
    ElMessage.error(error.message||'网络异常')
    return Promise.reject(error)
  }
)

export default service
