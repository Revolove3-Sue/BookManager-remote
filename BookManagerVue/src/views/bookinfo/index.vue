<template>
    <div class="app-container">
        <!-- 顶部功能 -->
        <div class="filter-container" style="margin-bottom: 15px">
            <!-- 书名输入 -->
            <el-input
                v-model="queryParam.bookName"
                placeholder="书名"
                style="width: 200px"
                class="filter-item"
                @keyup.enter.native="handleFilter"
            />
            <!-- 作者输入 -->
            <el-input
                v-model="queryParam.bookAuthor"
                placeholder="作者"
                style="width: 200px"
                class="filter-item"
                @keyup.enter.native="handleFilter"
            />
            <!-- 类型选择 -->
            <el-select
                v-model="queryParam.bookTypeId"
                filterable
                placeholder="类型"
                clearable
                class="filter-item"
                style="width: 200px"
            >
                <el-option
                    v-for="item in typeData"
                    :key="item.bookTypeId"
                    :label="item.bookTypeName"
                    :value="item.bookTypeId"
                />
            </el-select>
            <!-- 一些按钮 -->
            <br />
            <br />
            <el-button
                v-waves
                style="font-size: 18px"
                class="filter-item"
                type="primary"
                @click="handleFilter"
            >
                搜索
            </el-button>
            <el-button
                v-waves
                style="font-size: 18px"
                class="filter-item"
                type="primary"
                @click="handleShowAll"
            >
                
                显示全部
            </el-button>
            <el-button
                v-permission="['admin']"
                class="filter-item"
                style="margin-left: 10px; font-size: 18px"
                type="success"
                @click="handleCreate"
            >
                
                添加图书
            </el-button>
            <el-button
                v-permission="['admin']"
                class="filter-item"
                style="margin-left: 10px; font-size: 18px"
                type="danger"
                @click="handleDeleteSome"
            >
                
                批量删除
            </el-button>
        </div>

        <!--弹出框-->
        <el-dialog
            :title="formTitle"
            :visible.sync="dialogFormVisible"
            width="40%"
        >
            <el-row>
                <el-col :span="16">
                    <!--普通表单-->
                    <el-form
                        :model="form"
                        :rules="rules"
                        ref="ruleForm"
                        label-width="80px"
                    >
                        <el-form-item label="图书名称" prop="bookName">
                            <el-input v-model="form.bookName"></el-input>
                        </el-form-item>

                        <el-form-item label="作者" prop="bookAuthor">
                            <el-input v-model="form.bookAuthor"></el-input>
                        </el-form-item>

                        <el-form-item label="价格" prop="bookPrice">
                            <el-input v-model="form.bookPrice"></el-input>
                        </el-form-item>

                        <el-form-item label="图书类型" prop="bookTypeId">
                            <el-select
                                v-model="form.bookTypeId"
                                placeholder="请选择类型"
                            >
                                <el-option
                                    v-for="item in typeData"
                                    :key="item.bookTypeId"
                                    :label="item.bookTypeName"
                                    :value="item.bookTypeId"
                                >
                                </el-option>
                            </el-select>
                        </el-form-item>

                        <el-form-item label="书籍描述" prop="bookDesc">
                            <el-input
                                type="textarea"
                                v-model="form.bookDesc"
                            ></el-input>
                        </el-form-item>
                    </el-form>
                </el-col>
                <el-col :span="8">
                    <div align="center">
                        <h3>点击下方图片上传封面</h3>
                        <!--上传图片-->
                        <el-upload
                            class="avatar-uploader"
                            action="http://localhost:9111/BookManager/update/updateImg"
                            :show-file-list="false"
                            :on-success="handleAvatarSuccess"
                            :before-upload="beforeAvatarUpload"
                        >
                            <img
                                v-if="form.bookImg"
                                :src="
                                    $store.state.settings.baseApi + form.bookImg
                                "
                                class="avatar"
                                alt="封面无法显示"
                            />
                            <i
                                v-else
                                class="el-icon-plus avatar-uploader-icon"
                            ></i>
                        </el-upload>
                    </div>
                </el-col>
            </el-row>

            <div slot="footer" class="dialog-footer">
                <el-button
                    @click="dialogFormVisible = false"
                    style="font-size: 18px"
                >
                    
                    取 消</el-button
                >
                <el-button
                    type="primary"
                    @click="submitForm"
                    style="font-size: 18px"
                >
                    
                    确 定
                </el-button>
            </div>
        </el-dialog>

        <!--弹出框2-->
        <el-dialog
            title="选择用户"
            :visible.sync="dialogFormVisible2"
            width="400px"
        >
            <el-form :model="form2">
                <el-form-item label="用户名" prop="userId" label-width="80px">
                    <el-select
                        v-model="form2.bookTypeId"
                        placeholder="请选择用户"
                    >
                        <el-option
                            v-for="item in userData"
                            :key="item.userId"
                            :label="item.userName"
                            :value="item.userId"
                        >
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button
                    @click="dialogFormVisible2 = false"
                    style="font-size: 18px"
                >
                    
                    取 消</el-button
                >
                <el-button
                    type="primary"
                    @click="submitForm2"
                    style="font-size: 18px"
                >
                    
                    确 定</el-button
                >
            </div>
        </el-dialog>

        <!--数据表格-->
        <el-table
            ref="multipleTable"
            :data="tableData"
            border
            style="width: 100%"
        >
            <el-table-column fixed type="selection" width="55">
            </el-table-column>
            <el-table-column fixed prop="bookId" label="序号" width="100">
            </el-table-column>
            <el-table-column
                v-if="roleisAdmin === false"
                label="图书封面"
                width="155"
            >
                <template slot-scope="scope">
                    <el-image
                        :src="$store.state.settings.baseApi + scope.row.bookImg"
                        style="width: 130px; height: 180px"
                    ></el-image>
                </template>
            </el-table-column>
            <el-table-column
                prop="bookName"
                label="图书名称"
                width="150"
                show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
                prop="bookAuthor"
                label="图书作者"
                width="100"
                show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column prop="bookPrice" label="图书价格" width="100">
            </el-table-column>
            <el-table-column
                prop="bookTypeName"
                label="图书类型名"
                width="100"
                show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
                prop="bookDesc"
                label="图书描述"
                min-width="300"
                show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column label="图书状态" width="100">
                <template slot-scope="scope">
                    <span v-if="scope.row.isBorrowed === 1" style="color: red"
                        >已借出</span
                    >
                    <span v-else style="color: #1aac1a">未借出</span>
                </template>
            </el-table-column>
            <el-table-column
                fixed="right"
                label="操作"
                :width="roleisAdmin ? '440px' : '260px'"
            >
                <template slot-scope="scope">
                    <el-button
                        v-permission="['admin']"
                        @click="handleUpdate(scope.row)"
                        type="primary"
                        style="font-size: 18px"
                    >
                        
                        编辑</el-button
                    >
                    <el-button
                        v-permission="['admin']"
                        @click="handleDelete(scope.row, scope.$index)"
                        type="danger"
                        style="font-size: 18px"
                    >

                        删除</el-button
                    >
                    <el-button
                        @click="handleBorrow(scope.row)"
                        type="success"
                        style="font-size: 18px"
                    >

                        借阅图书</el-button
                    >
                </template>
            </el-table-column>
        </el-table>

        <!--分页条-->
        <el-pagination
            background
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page.sync="queryParam.page"
            :page-sizes="[5, 10, 20, 50]"
            :page-size="queryParam.limit"
            layout="total, sizes, prev, pager, next, jumper"
            :total="recordTotal"
            style="margin-top: 15px"
        >
        </el-pagination>
    </div>
</template>

<script>
import { mapGetters } from "vuex";
import permission from "@/directive/permission/index.js"; // 权限判断指令
import waves from "@/directive/waves"; // waves directive

import {
    getCount,
    queryBookInfosByPage,
    addBookInfo,
    deleteBookInfo,
    deleteBookInfos,
    updateBookInfo,
} from "@/api/bookinfo";
import { queryBookTypes } from "@/api/booktype";
import { borrowBook } from "@/api/borrow";
import { queryUsers } from "@/api/user";

export default {
    name: "Bookinfo",
    directives: { waves, permission },
    // 创建后
    created() {
        // 从服务器获取数据表格第一页的信息
        queryBookInfosByPage(this.queryParam).then((res) => {
            console.log("首页数据获取成功", res);
            this.tableData = res.data;
            this.recordTotal = res.count;
        });
        // 从服务器获取所有的图书类型
        queryBookTypes().then((res) => {
            console.log("图书类型获取成功", res);
            this.typeData = res;
        });
    },
    mounted() {
        if (this.roleisAdmin === false) {
            this.queryParam.limit = 5;
            this.handleSizeChange(this.queryParam.limit);
        }
    },
    methods: {
        // 分页大小改变监听
        handleSizeChange(curSize) {
            const params = this.queryParam;
            params.limit = curSize;
            queryBookInfosByPage(params).then((res) => {
                console.log("分页数据获取成功", res);
                this.tableData = res.data;
                this.recordTotal = res.count;
            });
        },

        // 点击分页监听方法
        handleCurrentChange(curPage) {
            const params = this.queryParam;
            params.page = curPage;

            queryBookInfosByPage(params).then((res) => {
                console.log("分页数据获取成功", res);
                this.tableData = res.data;
                this.recordTotal = res.count;
            });
        },

        // 搜索图书
        handleFilter() {
            this.queryParam.page = 1;
            queryBookInfosByPage(this.queryParam).then((res) => {
                if (res.code === 0) {
                    this.tableData = res.data;
                    this.recordTotal = res.count;
                }
            });
        },

        // 显示全部
        handleShowAll() {
            this.queryParam.page = 1;
            this.queryParam.bookName = null;
            this.queryParam.bookAuthor = null;
            this.queryParam.bookTypeId = null;
            queryBookInfosByPage(this.queryParam).then((res) => {
                if (res.code === 0) {
                    this.tableData = res.data;
                    this.recordTotal = res.count;
                }
            });
        },

        // 图片上传成功监听
        handleAvatarSuccess(res, file) {
            console.log(res);
            console.log(file);
            if (res.code === 0) {
                this.$message.success("上传成功");
                this.form.bookImg = res.data;
            } else {
                this.$message.error("上传失败，请联系管理员");
            }
        },

        // 图片上传之前监听
        beforeAvatarUpload(file) {
            const isJPG = file.type === "image/jpeg";
            const isLt2M = file.size / 1024 / 1024 < 2;

            if (!isJPG) {
                this.$message.error("上传封面图片只能是 JPG 格式!");
            }
            if (!isLt2M) {
                this.$message.error("上传封面图片大小不能超过 2MB!");
            }
            return isJPG && isLt2M;
        },

        // 点击添加记录
        handleCreate() {
            // 从服务器获取所有的图书类型
            queryBookTypes().then((res) => {
                console.log("图书类型获取成功", res);
                this.typeData = res;
            });
            // 表单是添加状态
            this.formType = 0;
            // 将空数据置入form
            this.form = {
                bookId: null,
                bookName: "",
                bookAuthor: "",
                bookPrice: "",
                bookTypeId: 1,
                bookDesc: "",
                isBorrowed: 0,
                bookImg: "",
            };
            // 显示表单框
            this.dialogFormVisible = true;
        },

        // 点击编辑记录
        handleUpdate(row) {
            // 从服务器获取所有的图书类型
            queryBookTypes().then((res) => {
                console.log("图书类型获取成功", res);
                this.typeData = res;
            });
            // 表单是编辑状态
            this.formType = 1;
            // 将行数据置入form
            this.form = {
                bookId: row.bookId,
                bookName: row.bookName,
                bookAuthor: row.bookAuthor,
                bookPrice: row.bookPrice,
                bookTypeId: row.bookTypeId,
                bookDesc: row.bookDesc,
                isBorrowed: row.isBorrowed,
                bookImg: row.bookImg,
            };
            // 显示表单框
            this.dialogFormVisible = true;
        },

        // 点击借阅图书
        handleBorrow(row) {
            if (this.roleisAdmin) {
                // 显示表单框
                this.dialogFormVisible2 = true;
                // 获取图书信息
                this.form2.bookId = row.bookId;

                // 获取用户信息
                queryUsers().then((res) => {
                    this.userData = res;
                });
            } else {
                this.$confirm("您确定要借书吗?", "提示").then(() => {
                    borrowBook(this.id, row.bookId).then((res) => {
                        if (res === 1) {
                            this.$message.success("借书成功");
                            this.handleCurrentChange(this.queryParam.page);
                        } else {
                            this.$message.error("借书失败");
                        }
                        this.dialogFormVisible2 = false; // 关闭对话框
                    });
                });
            }
        },

        // 添加和更新的提交表单
        submitForm() {
            if (this.formType === 0) {
                // 添加记录
                addBookInfo(this.form).then((res) => {
                    if (res === 1) {
                        this.$message.success("添加记录成功");
                        // 跳转到末尾
                        getCount().then((res) => {
                            this.recordTotal = res;
                            this.queryParam.page = 1;
                            this.handleCurrentChange(this.queryParam.page);
                        });
                    } else {
                        this.$message.error("添加记录失败");
                    }
                    this.dialogFormVisible = false; // 关闭对话框
                });
            } else if (this.formType === 1) {
                //更新记录
                updateBookInfo(this.form).then((res) => {
                    if (res === 1 || res === 0) {
                        this.$message.success("更新记录成功");
                        this.handleCurrentChange(this.queryParam.page);
                    } else {
                        this.$message.error("更新记录失败");
                    }
                    this.dialogFormVisible = false; // 关闭对话框
                });
            }
        },

        // 借书的提交表单
        submitForm2() {
            borrowBook(this.form2.userId, this.form2.bookId).then((res) => {
                if (res === 1) {
                    this.$message.success("借书成功");
                    this.handleCurrentChange(this.queryParam.page);
                } else {
                    this.$message.error("借书失败");
                }
                this.dialogFormVisible2 = false; // 关闭对话框
            });
        },

        // 删除记录
        handleDelete(row, index) {
            this.$confirm("确定要删除该条记录吗?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            }).then(() => {
                deleteBookInfo(row).then((res) => {
                    if (res === 1) {
                        this.$message.success("删除记录成功");
                        this.tableData.splice(index, 1);
                        // 如果删完了，获取上一页
                        if (this.tableData.length === 0) {
                            this.queryParam.page = this.queryParam.page - 1;
                            this.handleCurrentChange(this.queryParam.page);
                        }
                    } else if (res === -1) {
                        this.$message.error(
                            "该图书存在与读者的借阅信息，请尝试先删除所有的本图书借阅信息再重试"
                        );
                    } else {
                        this.$message.error("删除记录失败");
                    }
                });
            });
        },

        // 删除一些
        handleDeleteSome() {
            this.$confirm("确定要删除这些记录吗?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            }).then(() => {
                // 获取选中的对象数组
                const items = this.$refs.multipleTable.selection;
                deleteBookInfos(items).then((res) => {
                    if (res > 0) {
                        this.$message.success("删除" + res + "条记录成功");
                        if (this.tableData.length === res) {
                            //如果本页内容全部删光了
                            //当前页为上一页
                            if (this.queryParam.page !== 0) {
                                this.queryParam.page = this.queryParam.page - 1;
                            }
                        }
                        // 重载当前页
                        this.handleCurrentChange(this.queryParam.page);
                    } else {
                        this.$message.error("删除记录失败");
                    }
                });
            });
        },
    },
    data() {
        return {
            // 表格数据
            tableData: [],
            // 记录总数
            recordTotal: 0,
            // 图书类型数据
            typeData: [],
            // 用户数据
            userData: [],
            // 查询参数
            queryParam: {
                page: 1,
                limit: 10,
                bookName: null,
                bookAuthor: null,
                bookTypeId: null,
            },
            // 对话框表单显示
            dialogFormVisible: false,
            dialogFormVisible2: false,
            // 表单类型（添加数据:0,修改数据:1）
            formType: 0,
            // 表单数据
            form: {
                bookId: null,
                bookName: "",
                bookAuthor: "",
                bookPrice: 0,
                bookTypeId: 1,
                bookDesc: "",
                isBorrowed: 0,
                bookImg: "",
            },
            form2: {
                userId: 1,
                bookId: 1,
            },
            rules: {
                bookName: [
                    {
                        required: true,
                        message: "请输入图书名称",
                        trigger: "blur",
                    },
                ],
                bookAuthor: [
                    { required: true, message: "请输入作者", trigger: "blur" },
                ],
                bookPrice: [
                    { required: true, message: "请输入价格", trigger: "blur" },
                ],
                bookTypeId: [
                    { required: true, message: "请选择类型", trigger: "blur" },
                ],
                bookDesc: [
                    { required: true, message: "请输入描述", trigger: "blur" },
                ],
                isBorrowed: [
                    { required: true, message: "请选择状态", trigger: "blur" },
                ],
            },
        };
    },
    computed: {
        // 获得user信息
        ...mapGetters(["id", "name", "roles"]),
        // 通过表单类型计算表单标题
        formTitle() {
            return this.formType === 0 ? "添加记录" : "修改记录";
        },
        roleisAdmin() {
            if (this.roles[0] === "admin") return true;
            else return false;
        },
    },
};
</script>

<style scoped>
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
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
}
.avatar {
    width: 150px;
    height: 200px;
    display: block;
}
</style>
