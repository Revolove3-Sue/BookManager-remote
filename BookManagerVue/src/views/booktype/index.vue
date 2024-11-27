<template>
    <div class="app-container">
        <!-- 顶部功能 -->
        <div class="filter-container" style="margin-bottom: 15px">
            <!-- 类型名输入 -->
            <el-input
                v-model="queryParam.bookTypeName"
                placeholder="类型名"
                style="width: 200px"
                class="filter-item"
                @keyup.enter.native="handleFilter"
            />
            <br />
            <br />
            <!-- 一些按钮 -->
            <el-button
                v-waves
                class="filter-item"
                style="font-size: 18px"
                type="primary"
                @click="handleFilter"
            >
                
                搜索
            </el-button>
            <el-button
                v-waves
                class="filter-item"
                style="font-size: 18px"
                type="primary"
                @click="handleShowAll"
            >
                
                显示全部
            </el-button>
            <el-button
                class="filter-item"
                style="margin-left: 10px; font-size: 18px"
                type="success"
                @click="handleCreate"
            >
                
                添加类型
            </el-button>
            <el-button
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
            width="30%"
        >
            <!--普通表单-->
            <el-form
                :model="form"
                :rules="rules"
                ref="ruleForm"
                label-width="80px"
            >
                <el-form-item label="类型名称" prop="bookTypeName">
                    <el-input v-model="form.bookTypeName"></el-input>
                </el-form-item>

                <el-form-item label="类型描述" prop="bookTypeDesc">
                    <el-input
                        type="textarea"
                        v-model="form.bookTypeDesc"
                    ></el-input>
                </el-form-item>
            </el-form>

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
            <el-table-column fixed prop="bookTypeId" label="序号" width="100">
            </el-table-column>
            <el-table-column
                prop="bookTypeName"
                label="类型名称"
                show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
                prop="bookTypeDesc"
                label="类型描述"
                show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="260">
                <template slot-scope="scope">
                    <el-button
                        @click="handleUpdate(scope.row)"
                        type="primary"
                        style="font-size: 18px"
                    >
                        
                        编辑</el-button
                    >
                    <el-button
                        @click="handleDelete(scope.row, scope.$index)"
                        type="danger"
                        style="font-size: 18px"
                    >

                        删除</el-button
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

import waves from "@/directive/waves"; // waves directive
import {
    getCount,
    queryBookTypes,
    queryBookTypesByPage,
    addBookType,
    deleteBookType,
    deleteBookTypes,
    updateBookType,
} from "@/api/booktype";

export default {
    name: "Bookinfo",
    directives: { waves },
    // 创建后
    created() {
        // 从服务器获取数据表格第一页的信息
        queryBookTypesByPage(this.queryParam).then((res) => {
            console.log("首页数据获取成功", res);
            this.tableData = res.data;
            this.recordTotal = res.count;
            ;
        });
    },
    methods: {
        // 分页大小改变监听
        handleSizeChange(curSize) {
            const params = this.queryParam;
            params.limit = curSize;
            queryBookTypesByPage(params).then((res) => {
                console.log("分页数据获取成功", res);
                this.tableData = res.data;
                this.recordTotal = res.count;
            ;
            });
        },

        // 点击分页监听方法
        handleCurrentChange(curPage) {
            const params = this.queryParam;
            params.page = curPage;
            queryBookTypesByPage(params).then((res) => {
                console.log("分页数据获取成功", res);
                this.tableData = res.data;
                this.recordTotal = res.count;
                ;
            });
        },

        // 搜索
        handleFilter() {
            this.queryParam.page = 1;
            queryBookTypesByPage(this.queryParam).then((res) => {
                if (res.code === 0) {
                    this.tableData = res.data;
                    this.recordTotal = res.count;
                    ;
                }
            });
        },

        // 显示全部
        handleShowAll() {
            this.queryParam.page = 1;
            this.queryParam.bookTypeName = null;
            queryBookTypesByPage(this.queryParam).then((res) => {
                if (res.code === 0) {
                    this.tableData = res.data;
                    this.recordTotal = res.count;
                    ;
                }
            });
        },

        // 点击添加记录
        handleCreate() {
            // 表单是添加状态
            this.formType = 0;
            // 将空数据置入form
            this.form = {
                bookTypeId: null,
                bookTypeName: "",
                bookTypeDesc: "",
            };
            // 显示表单框
            this.dialogFormVisible = true;
        },

        // 点击编辑记录
        handleUpdate(row) {
            // 表单是编辑状态
            this.formType = 1;
            // 将行数据置入form
            this.form = {
                bookTypeId: row.bookTypeId,
                bookTypeName: row.bookTypeName,
                bookTypeDesc: row.bookTypeDesc,
            };
            // 显示表单框
            this.dialogFormVisible = true;
        },

        // 添加和更新的提交表单
        submitForm() {
            if (this.formType === 0) {
                // 添加记录
                addBookType(this.form).then((res) => {
                    if (res === 1) {
                        this.$message.success("添加记录成功");
                        // 跳转到末尾
                        getCount().then((res) => {
                            this.recordTotal = res;
                            this.queryParam.page = Math.ceil(
                                this.recordTotal / this.queryParam.limit
                            );
                            this.handleCurrentChange(this.queryParam.page);
                        });
                    } else {
                        this.$message.error("添加记录失败");
                    }
                    this.dialogFormVisible = false; // 关闭对话框
                });
            } else if (this.formType === 1) {
                //更新记录
                updateBookType(this.form).then((res) => {
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

        // 删除记录
        handleDelete(row, index) {
            this.$confirm("确定要删除该条记录吗?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            }).then(() => {
                deleteBookType(row).then((res) => {
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
                            "该图书类型下存在图书，请先删除所属的图书再尝试删除类型"
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
                deleteBookTypes(items).then((res) => {
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
    mounted() {
        
    },
    data() {
        return {
            // 表格数据
            tableData: [],
            // 记录总数
            recordTotal: 0,
            // 查询参数
            queryParam: {
                page: 1,
                limit: 10,
                bookTypeName: null,
            },
            // 对话框表单显示
            dialogFormVisible: false,
            // 表单类型（添加数据:0,修改数据:1）
            formType: 0,
            // 表单数据
            form: {
                bookId: null,
                bookTypeName: "",
                bookTypeDesc: "",
            },
            rules: {
                bookTypeName: [
                    {
                        required: true,
                        message: "请输入图书类型名称",
                        trigger: "blur",
                    },
                ],
                bookTypeDesc: [
                    {
                        required: true,
                        message: "请输入图书类型描述",
                        trigger: "blur",
                    },
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
