<template>
  <div class="app-container">

    <!--搜索表单-->
    <el-form :inline="true" :model="searchStudent" class="demo-form-inline">
      <el-form-item label="学员姓名">
        <el-input
          v-model="searchStudent.name"
          placeholder="请输入学员名称"
        ></el-input>
      </el-form-item>

      <el-form-item label="学号">
        <el-input
          v-model="searchStudent.registration"
          placeholder="请输入学号"
        ></el-input>
      </el-form-item>

      <el-form-item label="最高学历">
        <el-select v-model="searchStudent.education" placeholder="请选择">
          <el-option label="初中" value="1"></el-option>
          <el-option label="高中" value="2"></el-option>
          <el-option label="大专" value="3"></el-option>
          <el-option label="本科" value="4"></el-option>
          <el-option label="硕士" value="5"></el-option>
          <el-option label="博士" value="6"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="所属班级">
        <el-select v-model="searchStudent.classes" placeholder="请选择">
          <el-option
              v-for="item in classList"
              :key="item.value"
              :label="item.name"
              :value="item.id"
            />
        </el-select>
      </el-form-item>


      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
         <el-button type="info" @click="clear">清空</el-button>
      </el-form-item>
    </el-form>



    <!--按钮-->
    <el-row>
      <el-button type="danger" size="medium" @click="deleteByIds">- 批量删除</el-button>
      <el-button type="primary" size="medium" @click="addDialogVisible = true;" >+ 添加学员</el-button>
    </el-row>

    <!--添加数据对话框表单-->
    <el-dialog ref="studentDialog" title="编辑学员" :visible.sync="editDialogVisible" width="30%">
      <el-form ref="editStudentForm" :model="editStudent" :rules="rules" label-width="80px" size="mini">
        <el-form-item label="姓名" :rules="rules.name" prop="name">
          <el-input v-model="editStudent.name"></el-input>
        </el-form-item>
        <el-form-item label="学号" :rules="rules.registration" prop="registration">
          <el-input v-model="editStudent.registration"></el-input>
        </el-form-item>

        <el-form-item label="性别" :rules="rules.gender" prop="gender">
          <el-select v-model="editStudent.gender" placeholder="请选择" style="width:100%" >
             <el-option
              v-for="item in genderList"
              :key="item.value"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="手机号" :rules="rules.phone" prop="phone">
          <el-input v-model="editStudent.phone"></el-input>
        </el-form-item>

        <el-form-item label="最高学历">
          <el-select v-model="editStudent.education" placeholder="请选择" value-key="student.education" style="width:100%">
            <el-option
              v-for="item in educationList"
              :key="item.value"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>


        <el-form-item label="所属班级" prop="classId">
          <el-select v-model="editStudent.classId" placeholder="请选择" style="width:100%">
            <!-- 
            <el-option label="学工部" value="1"></el-option>
            <el-option label="教研部" value="2"></el-option>
             -->
            <el-option
              v-for="item in classList"
              :key="item.value"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="validateForm('editStudentForm')">提交</el-button>
          <el-button @click="editDialogVisible = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-dialog ref="studentDialog" title="新增学员" :visible.sync="addDialogVisible" width="30%">
      <el-form ref="newStudentForm" :model="newStudent" :rules="rules" label-width="80px" size="mini">
        <el-form-item label="姓名" :rules="rules.name" prop="name">
          <el-input v-model="newStudent.name"></el-input>
        </el-form-item>
        <el-form-item label="学号" :rules="rules.registration" prop="registration">
          <el-input v-model="newStudent.registration"></el-input>
        </el-form-item>

        <el-form-item label="性别" :rules="rules.gender" prop="gender">
          <el-select v-model="newStudent.gender" placeholder="请选择" style="width:100%" >
             <el-option
              v-for="item in genderList"
              :key="item.value"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="手机号" :rules="rules.phone" prop="phone">
          <el-input v-model="newStudent.phone"></el-input>
        </el-form-item>

        <el-form-item label="最高学历">
          <el-select v-model="newStudent.education" placeholder="请选择" value-key="student.education" style="width:100%">
            <el-option
              v-for="item in educationList"
              :key="item.value"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>


        <el-form-item label="所属班级" :rules="rules.classes" prop="classes">
          <el-select v-model="newStudent.classId" placeholder="请选择" style="width:100%">
            <!-- 
            <el-option label="学工部" value="1"></el-option>
            <el-option label="教研部" value="2"></el-option>
             -->
            <el-option
              v-for="item in classList"
              :key="item.value"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="validateForm('newStudentForm')">提交</el-button>
          <el-button @click="addDialogVisible = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-dialog ref="form" title="学员违纪处理" :visible.sync="dialogVisibleDis" width="30%">
      <el-form ref="form" label-width="80px" size="mini">
        <el-form-item label="违纪扣分">
          <el-input v-model="score">{{score}}</el-input>
        </el-form-item>
      

        <el-form-item>
          <el-button type="primary" @click="dis(student.id,score)">提交</el-button>
          
          <el-button @click="dialogVisibleDis = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <br>
    <!--表格-->
    <template>
      <el-table :data="tableData" style="width: 100%" border @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"  align="center"></el-table-column>
        <el-table-column  prop="name" width="100" label="姓名"  align="center"></el-table-column>
        <el-table-column  prop="registration"  label="学号"  align="center"></el-table-column>

        <el-table-column  prop="classes"  label="班级"  align="center">
          <template slot-scope="scope">
            <span style="margin-right: 10px">{{getClassName(scope.row.classId)}}</span>
          </template>
        </el-table-column>

        <el-table-column align="center" width="80" label="性别">
          <template slot-scope="scope">
            <span style="margin-right: 10px">
            {{scope.row.gender == "1" ? "男" : "女"}}</span>
          </template>
        </el-table-column>

        <el-table-column  prop="phone"  label="手机号"  align="center"></el-table-column>

        <el-table-column align="center" width="80" label="最高学历">
          <template slot-scope="scope">
            <span style="margin-right: 10px" v-if="scope.row.education == 1">初中</span>
            <span style="margin-right: 10px" v-if="scope.row.education == 2">高中</span>
            <span style="margin-right: 10px" v-if="scope.row.education == 3">大专</span>
            <span style="margin-right: 10px" v-if="scope.row.education == 4">本科</span>
            <span style="margin-right: 10px" v-if="scope.row.education == 5">硕士</span>
            <span style="margin-right: 10px" v-if="scope.row.education == 6">博士</span>
          </template>
        </el-table-column>

        <el-table-column  prop="disciplinary" width="80" label="违纪次数"  align="center"></el-table-column>

        <el-table-column  prop="disciplinaryScore" width="80" label="违纪扣分"  align="center"></el-table-column>

        <el-table-column align="center" width="175" label="最后操作时间">
          <template slot-scope="scope">
            {{scope.row.updateTime}}
          </template>
        </el-table-column>

        <el-table-column align="center" width="250" label="操作">
          <template slot-scope="scope">
            <el-button type="primary" size="small" @click="showEditDialog(scope.row)">编辑</el-button>
            <el-button type="warning" size="small" @click="disVisible(scope.row.id)">违纪</el-button>
            <el-button type="danger" size="small" @click="deleteById(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </template>

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
import { page, add, update, deleteById, selectById ,dis} from "@/api/student.js";
import { findAll } from "@/api/classes.js";
import { getToken } from '@/utils/auth';

export default {
  data() {
    return {
      rules: {
      phone: [
        { required: true, message: '请输入手机号', trigger: 'blur' },
        { min: 11, max: 11, message: '手机号必须是11位', trigger: 'blur' },
        { pattern: /^[0-9]*$/, message: '手机号必须是数字', trigger: 'blur' }
      ],
      registration: [
        { required: true, message: '请输入学号', trigger: 'blur' },
        { min: 10, max: 10, message: '学号必须是10位', trigger: 'blur' },
        { pattern: /^[0-9]*$/, message: '学号必须是数字', trigger: 'blur' }
      ],
      name: [
        { required: true, message: '请输入姓名', trigger: 'blur' },
        { min: 2, max: 10, message: '姓名长度在 2 到 10 个字符', trigger: 'blur' }
      ],
      gender: [
        { required: true, message: '请选择性别', trigger: 'blur' }
      ],
      classId: [
        { required: true, message: '请选择班级', trigger: 'blur' }
      ]
    },  
      score:'',
      background: true,
      // 每页显示的条数
      pageSize: 5,
      // 总记录数
      totalCount: 100,
      // 当前页码
      currentPage: 1,
      // 添加数据对话框是否展示的标记
      // dialogVisible: false,
      dialogVisibleDis:false,
      addDialogVisible: false,
      editDialogVisible: false,
      newStudent: {},
      editStudent: {},
      
      // 品牌模型数据
      searchStudent: {
        name: "",
        registration: "",
        education:"",
        classes:""
      },
      student: {},
      classList: [],
      genderList: [{id: 1,name: "男"},{id: 2,name: "女"}],
      educationList: [
        {
          id: 1,
          name: "初中",
        },
        {
          id: 2,
          name: "高中",
        },
        {
          id: 3,
          name: "大专",
        },
        {
          id: 4,
          name: "本科",
        },
        {
          id: 5,
          name: "硕士",
        },
        {
          id: 6,
          name: "博士",
        }
      ],

      updateTime: "",

      // 被选中的id数组
      selectedIds: [],
      // 复选框选中数据集合
      multipleSelection: [],
      // 表格数据
      tableData: [],
      token: {token: getToken()}
    };
  },

  mounted() {
    this.page(); //当页面加载完成后，发送异步请求，获取数据
    findAll().then((result) => {
      console.log(result.data.data);
      this.classList = result.data.data;
      
    });
  },
  
  methods: {
    validateForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (formName === 'newStudentForm') {
            this.addNewStudent();
          } else if (formName === 'editStudentForm') {
            this.updateStudent();
          }
        } else {
          this.$message.error('表单中有未完成项，请完成后再提交！');
          return false;
        }
      });
    },
    addNewStudent(){
      add(this.newStudent).then((response) => {
        if (response.data.code === 1) {
          this.$message({ type: 'success', message: '新增学员成功!' });
          this.addDialogVisible = false;
          this.newStudent = {}; // 清空表单
          this.page(); // 重新加载数据
        } else {
          this.$message.error(response.data.msg);
        }
      });
    },
    updateStudent(){
      update(this.editStudent).then((response) => {
        if (response.data.code === 1) {
          this.$message({ type: 'success', message: '学员更新成功!' });
          this.editDialogVisible = false;
          this.page(); // 重新加载数据
        } else {
          this.$message.error(response.data.msg);
        }
      });
    },
    // add() {
    //   this.$refs.studentForm.validate(valid => {
    //     if (valid) {
    //       let operator = this.student.id ? update(this.student) : add(this.student);
    //       operator.then(resp => {
    //         if (resp.data.code === "1") {
    //           this.$message({ message: "保存成功", type: "success" });
    //           this.dialogVisible = false;
    //           this.page();
    //         } else {
    //           this.$message.error(resp.data.msg);
    //         }
    //       }).catch(err => {
    //         this.$message.error("请求失败：" + err);
    //       });
    //     } else {
    //       this.$message.error("表单验证未通过，请检查输入");
    //       return false; // 明确返回 false 以阻止进一步操作
    //     }
    //   });
    // },
    showEditDialog(studentData) {
      this.editStudent = { ...studentData };
      this.editDialogVisible = true;
    },
    //打开新增学员对话框
    openAddDialog() {
    this.newStudent = {}; // 重置学员对象
    this.dialogVisible = true; // 显示对话框
    // this.$refs.studentForm.resetFields();
    },
    // 查询分页数据
    page() {
      page(
        this.searchStudent.name,
        this.searchStudent.registration,
        this.searchStudent.education,
        this.searchStudent.classes,
        this.currentPage,
        this.pageSize
      ).then((res) => {
        this.totalCount = res.data.data.total;
        this.tableData = res.data.data.rows;
      });
    },

    // 复选框选中后执行的方法
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    getClassName(id) {
        const classes = this.classList.find(classes => classes.id === id);
        return classes ? classes.name : '未知';  // 如果找不到对应的ID，返回'未知'
    }, 

    // 查询方法
    onSubmit() {
      this.currentPage = 1;
      this.page();
    },

    clear(){
      this.searchStudent = {name: "", registration: "",education:"",classes:""};
      this.page();
    },
    // 添加数据
    // add() {
    //   let operator;

    //   if (this.student.id) {
    //     //修改
    //     operator = update(this.student);
    //   } else {
    //     operator = add(this.student);
    //   }

    //   operator.then((resp) => {
    //     if (resp.data.code == "1") {
    //       this.dialogVisible = false;
    //       this.page();
    //       this.$message({ message: "恭喜你，保存成功", type: "success" });
    //       // this.student = { image: "" };
    //     } else {
    //       this.$message.error(resp.data.msg);
    //     }
    //   });
    // },
    // update(id) {
    //   //1. 打开窗口
    //   this.editDialogVisible = true;
    //   //2. 发送请求

    //   selectById(id).then((result) => {
    //     if (result.data.code == 1) {
    //       this.student = result.data.data;
    //       this.student;
    //     }
    //   });
    // },


    //分页
    handleSizeChange(val) {
      // 重新设置每页显示的条数
      this.pageSize = val;
      this.page();
    },

    handleCurrentChange(val) {
      // 重新设置当前页码
      this.currentPage = val;
      this.page();
    },


    //删除员工信息
    deleteById(id){
      this.$confirm("此操作将删除该数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
          //2. 发送AJAX请求
          deleteById(id).then((resp) => {
            if (resp.data.code == 1) {
              //删除成功
              this.$message.success("恭喜你，删除成功");
              this.page();
            } else {
              this.$message.error(resp.data.msg);
            }
          });
      }).catch(() => {
          //用户点击取消按钮
          this.$message.info("已取消删除");
        });
    },
    dis(id,score){
      // this.dialogVisibleDis = true;
      dis(id,score).then((resp) => {
            if (resp.data.code == 1) {
              //删除成功
              this.$message.success("扣分成功");
              this.dialogVisibleDis = false;
              this.page();
            } else {
              this.$message.error(resp.data.msg);
            }
          }).catch(() => {
          //用户点击取消按钮
          this.$message.info("已取消扣分");
        });
    },
    disVisible(id){
      this.dialogVisibleDis = true;
      selectById(id).then((result) => {
        if (result.data.code == 1) {
          this.student = result.data.data;
          this.student;
        }
      });
    },

    // 批量删除员工信息
    deleteByIds() {
      // 弹出确认提示框
      this.$confirm("此操作将删除该数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
          //用户点击确认按钮
          //1. 创建id数组, 从 this.multipleSelection 获取即可
          for (let i = 0; i < this.multipleSelection.length; i++) {
            this.selectedIds[i] = this.multipleSelection[i].id;
          }

          //2. 发送AJAX请求
          deleteById(this.selectedIds).then((resp) => {
            if (resp.data.code == "1") {
              //删除成功
              this.$message.success("恭喜你，删除成功");
              this.page();
            } else {
              this.$message.error(resp.data.msg);
            }
          });
      }).catch(() => {
          //用户点击取消按钮
          this.$message.info("已取消删除");
        });
    },
  },


  // watch: {
  //   entrydate(val) {
  //     if (val && val.length >= 2) {
  //       this.beginTime = val[0];
  //       this.endTime = val[1];
  //     } else {
  //       this.beginTime = "";
  //       this.endTime = "";
  //     }
  //   },
  // },
};
</script>
<style>
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
