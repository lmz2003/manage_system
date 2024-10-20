<template>
  <div class="app-container">
    <!-- 搜索表单 -->
    <el-form :inline="true" :model="searchLecture" class="demo-form-inline">
      <el-form-item label="课程名称">
        <el-input v-model="searchLecture.name" placeholder="请输入课程名称"></el-input>
      </el-form-item>

      <el-form-item label="教室名称">
        <el-input v-model="searchLecture.room" placeholder="请输入教室名称"></el-input>
      </el-form-item>

      <el-form-item label="结课时间">
        <el-date-picker
          v-model="searchTime"
          clearable
          value-format="yyyy-MM-dd"
          type="daterange"
          placeholder="选择日期范围"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          style="width: 400px; margin-left: 20px"
        ></el-date-picker>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
        <el-button type="info" @click="clear">清空</el-button>
      </el-form-item>
    </el-form>

    <!-- 按钮 -->
    <el-row>
      <el-button type="primary" size="medium" @click="addDialogVisible = true;">+ 新增课程</el-button>
    </el-row>

    <!-- 新增课程对话框 -->
    <el-dialog title="新增课程" :visible.sync="addDialogVisible" width="30%">
      <el-form :model="newLecture" :rules="rules" ref="newLectureForm" label-width="80px" size="mini">
        <el-form-item label="课程名称" prop="name">
          <el-input v-model="newLecture.name"></el-input>
        </el-form-item>
        <el-form-item label="课程描述">
          <el-input type="textarea" v-model="newLecture.description"></el-input>
        </el-form-item>
        <el-form-item label="教室" prop="room">
          <el-input v-model="newLecture.room"></el-input>
        </el-form-item>
        <el-form-item label="开课时间" prop="beginTime">
          <el-date-picker
            v-model="newLecture.beginTime"
            value-format="yyyy-MM-dd"
            clearable
            type="date"
            placeholder="选择日期"
            size="small"
            style="width:100%"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="结课时间" prop="endTime">
          <el-date-picker
            v-model="newLecture.endTime"
            value-format="yyyy-MM-dd"
            clearable
            type="date"
            placeholder="选择日期"
            size="small"
            style="width:100%"
          ></el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="validateForm('newLectureForm')">保存</el-button>
          <el-button @click="addDialogVisible = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 编辑课程对话框 -->
    <el-dialog title="编辑课程" :visible.sync="editDialogVisible" width="30%">
      <el-form :model="editLecture" :rules="rules" ref="editLectureForm" label-width="80px" size="mini">
        <el-form-item label="课程名称" prop="name">
          <el-input v-model="editLecture.name"></el-input>
        </el-form-item>
        <el-form-item label="课程描述">
          <el-input type="textarea" v-model="editLecture.description"></el-input>
        </el-form-item>
        <el-form-item label="教室" prop="room">
          <el-input v-model="editLecture.room"></el-input>
        </el-form-item>
        <el-form-item label="开课时间" prop="beginTime">
          <el-date-picker
            v-model="editLecture.beginTime"
            clearable
            type="date"
            placeholder="选择日期"
            size="small"
            style="width:100%"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="结课时间" prop="endTime">
          <el-date-picker
            v-model="editLecture.endTime"
            clearable
            type="date"
            placeholder="选择日期"
            size="small"
            style="width:100%"
          ></el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="validateForm('editLectureForm')">保存</el-button>
          <el-button @click="editDialogVisible = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 表格 -->
    <el-table :data="tableData" style="width: 100%" border>
      <el-table-column type="index" width="100" label="序号" align="center"></el-table-column>
      <el-table-column prop="name" label="课程名称" align="center"></el-table-column>
      <el-table-column prop="room" label="教室" align="center"></el-table-column>
      <el-table-column prop="description" label="课程描述" align="center"></el-table-column>
      <el-table-column align="center" label="开课时间">
        <template slot-scope="scope">
          {{ scope.row.beginTime }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="结课时间">
        <template slot-scope="scope">
          {{ scope.row.endTime }}
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
      :total="totalCount"
    >
    </el-pagination>
  </div>
</template>

<script>
import { page, add, update, selectById, deleteById } from "@/api/lecture.js"; // 调整API路径和方法名称
import { getToken } from '@/utils/auth';

export default {
  data() {
    return {
      background: true,
      pageSize: 5,
      totalCount: 100,
      currentPage: 1,
      dialogVisible: false,
      searchLecture: {
        name: "",
        room: "",
      },
      lecture: {}, // 这是绑定对话框表单数据的对象
      beginTime: "",
      endTime: "",
      searchTime: ["", ""], // [开始时间, 结束时间
      newLecture: {}, // 用于新增课程的数据模型
      editLecture: {}, // 用于编辑课程的数据模型
      addDialogVisible: false,
      editDialogVisible: false,

      tableData: [],
      token: { token: getToken() },
      rules: {
        name: [
          { required: true, message: '请输入课程名称', trigger: 'blur' },
          { max: 20, message: '课程名称不能超过20个字符', trigger: 'blur' }
        ],
        room: [
          { required: true, message: '请输入教室名称', trigger: 'blur' },
          { max: 20, message: '教室名称不能超过20个字符', trigger: 'blur' }
        ],
        beginTime: [
          { required: true, message: '请选择开课时间', trigger: 'change' }
        ],
        endTime: [
          { required: true, message: '请选择结课时间', trigger: 'change' },
          { validator: this.validateEndTime, trigger: 'change' }
        ]
      }
    };
  },

  mounted() {
    this.page(); // 当页面加载完成后，发送异步请求，获取数据
  },

  methods: {
    // 验证结课时间必须大于开课时间
    validateEndTime(rule, value, callback) {
      const beginTime = this.newLecture.beginTime || this.editLecture.beginTime;
      if (beginTime && value && new Date(value) <= new Date(beginTime)) {
        callback(new Error('结课时间必须大于开课时间'));
      } else {
        callback();
      }
    },
    // 查询分页数据
    page() {
      page(
        this.searchLecture.name,
        this.searchLecture.room,
        this.searchTime[0],
        this.searchTime[1],
        this.currentPage,
        this.pageSize
      ).then((res) => {
        this.totalCount = res.data.data.total;
        this.tableData = res.data.data.rows;
      });
    },
    // 删除课程
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
    // 更新课程
    update(id) {
      this.dialogVisible = true;
      selectById(id).then((result) => {
        if (result.data.code === 1) {
          this.lecture = result.data.data;
        }
      });
    },
    // 清空搜索
    clear() {
      this.searchLecture = { name: "", room: "" };
      this.searchTime = ["", ""];
      this.page();
    },
    // 编辑课程，打开编辑对话框并加载数据
    edit(lecture) {
      this.editDialogVisible = true;
      this.editLecture = { ...lecture };
    },
    // 验证表单
    validateForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (formName === 'newLectureForm') {
            this.addNewLecture();
          } else if (formName === 'editLectureForm') {
            this.updateLecture();
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    // 添加或更新课程
    addNewLecture() {
      add(this.newLecture).then((resp) => {
        if (resp.data.code === 1) {
          this.addDialogVisible = false;
          this.$message({ message: "新增成功", type: "success" });
          this.page(); // 重新加载数据
          this.newLecture = {}; // 重置表单
        } else {
          this.$message.error(resp.data.msg);
        }
      });
    },
    // 更新已存在的课程
    updateLecture() {
      update(this.editLecture).then((resp) => {
        if (resp.data.code === 1) {
          this.editDialogVisible = false;
          this.$message({ message: "更新成功", type: "success" });
          this.page(); // 重新加载数据
        } else {
          this.$message.error(resp.data.msg);
        }
      });
    },
    // 分页处理
    handleSizeChange(val) {
      this.pageSize = val;
      this.page();
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.page();
    },
    onSubmit() {
      this.currentPage = 1;
      this.page();
    }
  }
};
</script>

<style scoped>
.app-container {
  margin: 20px;
}
</style>
