// empData.js
import request from '@/utils/request'

// 获取员工性别分布数据的函数
export function getGenderDistribution() {
  return request({
    url: '/emps/gender',
    method: 'get'
  });
}

// 获取员工职位的函数
export function getJobDistribution() {
  return request({
    url: '/emps/job',
    method: 'get'
  });
}