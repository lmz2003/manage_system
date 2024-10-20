<template>
  <div class="app-container">
    <!--搜索表单-->
    <el-form :inline="true" :model="searchClass" class="demo-form-inline">
      <el-form-item label="班级名称">
        <el-input v-model="searchClass.name" placeholder="请输入班级名称"></el-input>
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

    <!--按钮-->
    <el-row>
      <el-button type="primary" size="medium" @click="addDialogVisible = true;">+ 新增班级</el-button>
    </el-row>

    <!--新增数据对话框表单-->
      <el-dialog title="新增班级" :visible.sync="addDialogVisible" width="30%">
        <el-form :model="newClass" :rules="rules" ref="newClassForm" label-width="80px" size="mini">
          <el-form-item label="班级名称" prop="name">
            <el-input v-model="newClass.name"></el-input>
          </el-form-item>
          <el-form-item label="班级教室" prop="room">
            <el-input v-model="newClass.room"></el-input>
          </el-form-item>
          <el-form-item label="开课时间" prop="beginTime">
            <el-date-picker v-model="newClass.beginTime" clearable type="date" placeholder="选择日期" size="small" style="width:100%"></el-date-picker>
          </el-form-item>
          <el-form-item label="结课时间" prop="endTime">
            <el-date-picker v-model="newClass.endTime" clearable type="date" placeholder="选择日期" size="small" style="width:100%"></el-date-picker>
          </el-form-item>
          <el-form-item label="班主任" prop="empId">
            <el-select v-model="newClass.empId" placeholder="请选择" style="width:100%">
              <el-option v-for="item in masterList" :key="item.id" :label="item.name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="validateForm('newClassForm')">保存</el-button>
            <el-button @click="addDialogVisible = false">取消</el-button>
          </el-form-item>
        </el-form>
     </el-dialog>


    <!--编辑数据对话框表单-->
    <el-dialog title="编辑班级" :visible.sync="editDialogVisible" width="30%">
      <el-form :model="editClass" :rules="rules" ref="editClassForm" label-width="80px" size="mini">
        <el-form-item label="班级名称" prop="name">
          <el-input v-model="editClass.name"></el-input>
        </el-form-item>
        <el-form-item label="班级教室" prop="room">
          <el-input v-model="editClass.room"></el-input>
        </el-form-item>
        <el-form-item label="开课时间" prop="beginTime">
          <el-date-picker v-model="editClass.beginTime" clearable type="date" placeholder="选择日期" size="small" style="width:100%"></el-date-picker>
        </el-form-item>
        <el-form-item label="结课时间" prop="endTime">
          <el-date-picker v-model="editClass.endTime" clearable type="date" placeholder="选择日期" size="small" style="width:100%"></el-date-picker>
        </el-form-item>
        <el-form-item label="班主任" prop="empId">
          <el-select v-model="editClass.empId" placeholder="请选择" style="width:100%">
            <el-option v-for="item in masterList" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="validateForm('editClassForm')">保存</el-button>
          <el-button @click="editDialogVisible = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>


    <!--表格显示班级信息-->
    <el-table :data="tableData" style="width: 100%" border>
      <el-table-column type=index width="100" label="序号" align="center"></el-table-column>
      <el-table-column prop="name" width="300" label="班级名称" align="center"></el-table-column>
      <el-table-column prop="room" label="班级教室" align="center"></el-table-column>
      <el-table-column align="center" label="开课时间" :formatter="(row) => row.beginTime.split('T')[0]"></el-table-column>
      <el-table-column align="center" label="结课时间" :formatter="(row) => row.endTime.split('T')[0]"></el-table-column>
      <el-table-column prop="masterName" label="班主任" align="center">
        <template slot-scope="scope">
          {{ getMasterName(scope.row.empId) }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作">
        <template slot-scope="scope">
          <el-button type="primary" size="small" @click="showEditDialog(scope.row)">编辑</el-button>
          <el-button type="danger" size="small" @click="deleteById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页工具条-->
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
  </div>
</template>

<script>
import { page, add, update, selectById, deleteById } from "@/api/classes.js";
import { findAll } from "@/api/emp.js";
import { getToken } from '@/utils/auth';

export default {
  data() {
    return {
      background: true,
      pageSize: 5,
      totalCount: 100,
      currentPage: 1,
      addDialogVisible: false,
      editDialogVisible: false,
      searchClass: { name: "" },
      newClass: {},
      editClass: {},
      masterList: [],
      searchTime: ["", ""],
      tableData: [],
      token: {token: getToken()},
      rules: {
        name: [
          { required: true, message: '请输入班级名称', trigger: 'blur' },
          { max: 20, message: '班级名称不能超过20个字符', trigger: 'blur' }
        ],
        room: [
          { required: true, message: '请输入班级教室', trigger: 'blur' },
          { max: 20, message: '班级教室不能超过20个字符', trigger: 'blur' }
        ],
        beginTime: [
          { required: true, message: '请选择开课时间', trigger: 'change' }
        ],
        endTime: [
          { required: true, message: '请选择结课时间', trigger: 'change' },
          { validator: this.validateEndTime, trigger: 'change' }
        ],
        empId: [
          { required: true, message: '请选择班主任', trigger: 'change' }
        ]
      }
    };
  },

  mounted() {
    this.page();
    findAll().then((result) => {
      this.masterList = result.data.data.map(item => ({ id: item.id, name: item.name }));
    });
  },

  methods: {
    validateEndTime(rule, value, callback) {
      if (this.newClass.beginTime && value) {
        if (new Date(value) <= new Date(this.newClass.beginTime)) {
          callback(new Error('结课时间必须大于开课时间'));
        } else {
          callback();
        }
      } else {
        callback();
      }
    },
    page() {
      page(this.searchClass.name, this.searchTime[0], this.searchTime[1], this.currentPage, this.pageSize).then((res) => {
        this.totalCount = res.data.data.total;
        this.tableData = res.data.data.rows;
      });
    },
    validateForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (formName === 'newClassForm') {
            this.addNewClass();
          } else if (formName === 'editClassForm') {
            this.updateClass();
          }
        } else {
          this.$message.error('表单中有未完成项，请完成后再提交！');
          return false;
        }
        // console.log(this.$refs[formName]);
      });
    },
    addNewClass() {
      add(this.newClass).then((response) => {
        if (response.data.code === 1) {
          this.$message({ type: 'success', message: '新增班级成功!' });
          this.addDialogVisible = false;
          this.newClass = {}; // 清空表单
          this.page(); // 重新加载数据
        } else {
          this.$message.error(response.data.msg);
        }
      });
    },
    updateClass() {
      update(this.editClass).then((response) => {
        if (response.data.code === 1) {
          this.$message({ type: 'success', message: '班级更新成功!' });
          this.editDialogVisible = false;
          this.page(); // 重新加载数据
        } else {
          this.$message.error(response.data.msg);
        }
      });
    },
    deleteById(id) {
      this.$confirm('此操作将永久删除该班级, 是否继续?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteById(id).then((response) => {
          if (response.data.code === 1) {
            this.$message({ type: 'success', message: '删除成功!' });
            this.page(); // 重新加载数据
          } else {
            this.$message.error(response.data.msg);
          }
        });
      }).catch(() => {
        this.$message.info('已取消删除');
      });
    },
    showEditDialog(classData) {
      this.editClass = { ...classData };
      this.editDialogVisible = true;
    },
    clear() {
      this.searchClass = { name: "" };
      this.searchTime = ["", ""];
      this.page();
    },
    onSubmit() {
      this.currentPage = 1;
      this.page();
    },
    handleSizeChange(newSize) {
      this.pageSize = newSize;
      this.page();
    },
    handleCurrentChange(newPage) {
      this.currentPage = newPage;
      this.page();
    },
    getMasterName(empId) {
      const master = this.masterList.find(master => master.id === empId);
      return master ? master.name : '未知';
    }
  }
};
</script>

<style>
.app-container {
  margin: 20px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 100px;
  height: 100px;
  line-height: 100px;
  text-align: center;
}
.avatar {
  width: 100px;
  height: 100px;
  display: block;
}
</style>


