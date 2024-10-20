// empData.js
import request from '@/utils/request'

// 获取学员性别分布数据的函数
export function getGenderDistribution() {
  return request({
    url: '/student/gender',
    method: 'get'
  });
}

// 获取学员班级的函数
export function getClassDistribution() {
  return request({
    url: '/student/class',
    method: 'get'
  });
}