import request from '@/utils/request';
import axios from 'axios';

export function findAll() {
    return request({
        url: '/lecture/all',
        method: 'get'
    });
}

export function add(lecture) {
    return request({
        url: '/lecture',
        method: 'post',
        data: lecture
    });
}

export function update(lecture) {
    return request({
        url: '/lecture',
        method: 'put',
        data: lecture
    });
}

export function deleteById(id) {
    return request({
        url: '/lecture/' + id,
        method: 'delete'
    });
}

export function selectById(id) {
    return request({
        url: '/lecture/' + id,
        method: 'get'
    });
}

export function page(name, room, begin, end, page, pageSize) {
    return request({
        url: '/lecture?name='+name+'&room='+room+'&begin='+begin+'&end='+end+'&page='+page+'&pageSize='+pageSize,
        method: 'get'
    });
}

export function bindLectureTeacher(courseId, addIds, removeIds) {
    return request({
        url: '/lecture/bind',
        method: 'put',
        data: courseId, addIds, removeIds,
        headers: {
            'Content-Type': 'application/json'
        },
    });
}

export function getRelatedTeachers(id) {
    return request({
        url: '/lecture/teacher/' + id,
        method: 'get',
        
    });
}