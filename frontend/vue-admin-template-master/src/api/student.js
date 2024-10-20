import request from '@/utils/request'

export function findAll() {
    return request({
        url: '/student/all',
        method: 'get'
    })
}

export function add(student) {
    return request({
        url: '/student',
        method: 'post',
        data: student
    })
}

export function update(student) {
    return request({
        url: '/student',
        method: 'put',
        data: student
    })
}

export function deleteById(id) {
    return request({
      url: '/student/' + id,
      method: 'delete'
    })
  }
  
  export function selectById(id) {
    return request({
      url: '/student/' + id,
      method: 'get'
    })
  }
  
  export function page(name,registration,education,classId,page,pageSize) {
    return request({
      url: '/student?name='+name+'&registration='+registration+'&education='+education+'&classId='+classId+'&page='+page+'&pageSize='+pageSize,
      method: 'get'
    })
  }
  export function dis(id,score) {
    return request({
      url: '/student/dis?id=' + id+'&score='+score,
      method: 'get'
    })
  }