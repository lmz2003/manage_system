<template>
    <div class="app-container">
  
      <!-- 表格显示课程信息 -->
      <el-table :data="tableData" style="width: 100%" border>
        <el-table-column type="index" width="100" label="序号" align="center"></el-table-column>
        <el-table-column prop="name" label="课程名称" align="center"></el-table-column>
        <el-table-column prop="teacher" label="授课教师" align="center">
          <template slot-scope="scope">
            {{ scope.row.teacherNames }}
          </template>
        </el-table-column>
        <el-table-column align="center" label="操作">
          <template slot-scope="scope">
            <el-button type="primary" size="small" @click="edit(scope.row)">编辑</el-button>
            <el-button type="danger" size="small" @click="deleteById(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
  
      <!-- 分页工具条 -->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :background="background"
        :current-page="currentPage"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="5"
        layout="total, sizes, prev, pager, next, jumper"
        :total="totalCount">
      </el-pagination>
  
      <!-- 编辑课程对话框 -->
      <el-dialog title="编辑课程" :visible.sync="editDialogVisible" width="30%">
        <el-form :model="editLecture" label-width="80px" size="mini">
          <el-form-item label="课程名称">
            <el-input v-model="editLecture.name" disabled></el-input>
          </el-form-item>
          <el-form-item label="授课教师">
            <el-select v-model="editLecture.teachers" multiple placeholder="选择教师">
              <el-option
                v-for="teacher in teachers"
                :key="teacher.id"
                :label="teacher.name"
                :value="teacher.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="updateLecture">保存</el-button>
            <el-button @click="editDialogVisible = false">取消</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>
  </template>
  
  <script>
  import { page, bindLectureTeacher , deleteById, selectById, getRelatedTeachers } from "@/api/lecture.js"; // 调整API路径和方法名称
  import { findAllTeachers } from "@/api/emp.js"; // API获取所有讲师
  
  export default {
    data() {
      return {
        background: true,
        pageSize: 5,
        totalCount: 100,
        currentPage: 1,
        editDialogVisible: false,
        editLecture: {},
        tableData: [],
        teachers: [], // 讲师列表
      };
    },
    mounted() {
      this.page();
      this.fetchTeachers();
    },
    methods: {
      getTeacherNames(ids) {
        return getRelatedTeachers(ids).then((res) => {
      return res.data.data.join(', ');
      });
      },
      fetchTeachers() {
        findAllTeachers().then(response => {
          this.teachers = response.data.data;
          console.log(this.teachers)
        });
      },
      page() {
        page('', '', '','', this.currentPage, this.pageSize).then(async (res) => {
          this.totalCount = res.data.data.total;
          let rowsWithTeachers = await Promise.all(res.data.data.rows.map(async (row) => {
            let teachersData = await getRelatedTeachers(row.id);
            console.log(teachersData)
            let teacherNames = teachersData.data.data.map(t => t.name).join(', ');  // 解析教师名称
            let teacherIds = teachersData.data.data.map(t => t.id);  // 解析教师ID
            return {...row, teacherNames, teacherIds};  // 向每一行数据添加教师名称和ID
          }));
          this.tableData = rowsWithTeachers;  // 更新表格数据
        });
      },
      edit(lecture) {
        this.editLecture = { ...lecture, teachers: lecture.teacherIds || [] };
        // 保存原始教师ID数组以便比较
        this.originalTeacherIds = [...(lecture.teacherIds || [])];
        this.editDialogVisible = true;
      },
        updateLecture() {
          const dto = {
            courseId: this.editLecture.id,
            addIds: this.editLecture.teachers.filter(id => !this.originalTeacherIds.includes(id)),
            removeIds: this.originalTeacherIds.filter(id => !this.editLecture.teachers.includes(id))
          };

          if (dto.addIds.length || dto.removeIds.length) {
              bindLectureTeacher(dto).then((resp) => {
                  if (resp.data.code === 1) {
                      this.$message({ message: "课程和教师信息更新成功", type: "success" });
                      this.editDialogVisible = false;
                      this.page(); // 重新加载课程数据以显示更新
                  } else {
                      this.$message.error("更新失败: " + resp.data.msg);
                  }
              }).catch(error => {
                  this.$message.error("请求错误: " + error.message);
              });
          } else {
              this.$message({ message: "没有变化，无需更新", type: "info" });
              this.editDialogVisible = false;
          }
      },


      deleteById(id) {
        this.$confirm("确认删除?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          deleteById(id).then((result) => {
            if (result.data.code === 1) {
              this.$message({
                message: "删除成功",
                type: "success"
              });
              this.page(); // 重新加载数据
            } else {
              this.$message.error(result.data.msg);
            }
          });
        }).catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
      },
      handleSizeChange(newSize) {
        this.pageSize = newSize;
        this.page();
      },
      handleCurrentChange(newPage) {
        this.currentPage = newPage;
        this.page();
      },
    }
  };
  </script>
  
  <style scoped>
  .app-container {
    margin: 20px;
  }
  </style>
  