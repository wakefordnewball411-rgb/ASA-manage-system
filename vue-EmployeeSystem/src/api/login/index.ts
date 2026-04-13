import request from '@/utils/request'

// 管理员登录参数类型
export interface AdminLoginParams {
  username: string
  password: string
}

// 员工登录参数类型
export interface EmployeeLoginParams {
  name: string
  department: string
}

// 管理员登录
export function adminLogin(data: AdminLoginParams) {
  return request({
    url: '/login',
    method: 'post',
    data
  })
}

// 员工登录
export function employeeLogin(data: EmployeeLoginParams) {
  return request({
    url: '/login',
    method: 'post',
    data
  })
}