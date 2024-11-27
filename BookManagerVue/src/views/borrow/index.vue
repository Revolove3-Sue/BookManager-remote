<template>
    <div class="app-container">
        <!-- 顶部功能 -->
        <div class="filter-container" style="margin-bottom: 15px">
            <!-- 用户名输入 -->
            <el-input
                v-permission="['admin']"
                v-model="queryParam.userName"
                placeholder="用户名"
                style="width: 200px"
                class="filter-item"
                @keyup.enter.native="handleFilter"
            />
            <!-- 图书名输入 -->
            <el-input
                v-model="queryParam.bookName"
                placeholder="图书名"
                style="width: 200px"
                class="filter-item"
                @keyup.enter.native="handleFilter"
            />
            <br /><br />
            <!-- 一些按钮 -->
            <el-button
                v-waves
                class="filter-item"
                type="primary"
                style="font-size: 18px"
                @click="handleFilter"
            >
                
                搜索
            </el-button>
            <el-button
                v-waves
                class="filter-item"
                type="primary"
                style="font-size: 18px"
                @click="handleShowAll"
            >
                
                显示全部
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

        <!--数据表格-->
        <el-table
            ref="multipleTable"
            :data="tableData"
            border
            style="width: 100%"
        >
            <el-table-column fixed type="selection" width="55">
            </el-table-column>
            <el-table-column fixed prop="borrowId" label="序号" width="100">
            </el-table-column>
            <el-table-column
                prop="userName"
                label="用户名"
                show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
                prop="bookName"
                label="图书名"
                show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column prop="borrowTimeStr" label="借书时间">
            </el-table-column>
            <el-table-column label="还书时间" >
                <template slot-scope="scope">
                    <span
                        v-if="
                            scope.row.returnTimeStr === null ||
                            scope.row.returnTimeStr === ''
                        "
                        style="color: red"
                        >等待还书</span
                    >
                    <span v-else style="color: #1aac1a">{{
                        scope.row.returnTimeStr
                    }}</span>
                </template>
            </el-table-column>
            <el-table-column
                fixed="right"
                label="操作"
                :width="roleisAdmin ? '320px' : '180px'"
            >
                <template slot-scope="scope">
                    <el-button
                        v-permission="['admin']"
                        @click="handleDelete(scope.row, scope.$index)"
                        type="danger"
                        style="font-size: 18px"
                    >

                        删除</el-button
                    >
                    <el-button
                        v-if="
                            scope.row.returnTimeStr === null ||
                            scope.row.returnTimeStr === ''
                        "
                        @click="handleReturn(scope.row, scope.$index)"
                        type="success"
                        style="font-size: 18px"
                    >

                        归还图书</el-button
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
    queryBorrows,
    queryBorrowsByPage,
    addBorrow,
    deleteBorrow,
    deleteBorrows,
    updateBorrow,
    returnBook,
} from "@/api/borrow";


export default {
    name: "Bookinfo",
    directives: { waves, permission },
    // 创建后
    created() {
        // 从服务器获取数据表格第一页的信息
        queryBorrowsByPage(this.queryParam).then((res) => {
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
            queryBorrowsByPage(params).then((res) => {
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
            queryBorrowsByPage(params).then((res) => {
                console.log("分页数据获取成功", res);
                this.tableData = res.data;
                this.recordTotal = res.count;
                ;
            });
        },

        // 搜索
        handleFilter() {
            this.queryParam.page = 1;
            queryBorrowsByPage(this.queryParam).then((res) => {
                if (res.code === 0) {
                    this.tableData = res.data;
                    this.recordTotal = res.count;
                }
                ;
            });
        },

        // 显示全部
        handleShowAll() {
            this.queryParam.page = 1;
            this.queryParam.userName = null;
            this.queryParam.bookName = null;
            queryBorrowsByPage(this.queryParam).then((res) => {
                if (res.code === 0) {
                    this.tableData = res.data;
                    this.recordTotal = res.count;
                }
                ;
            });
        },

        // 删除记录
        handleDelete(row, index) {
            this.$confirm("确定要删除该条记录吗?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            }).then(() => {
                deleteBorrow(row).then((res) => {
                    if (res === 1) {
                        this.$message.success("删除记录成功");
                        this.tableData.splice(index, 1);
                        // 如果删完了，获取上一页
                        if (this.tableData.length === 0) {
                            this.queryParam.page = this.queryParam.page - 1;
                            this.handleCurrentChange(this.queryParam.page);
                        }
                    } else {
                        this.$message.error("存在未完成的借阅信息，删除失败");
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
                deleteBorrows(items).then((res) => {
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

        // 还书
        handleReturn(row, index) {
            this.$confirm("确定要还书吗?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            }).then(() => {
                returnBook(row.borrowId, row.bookId).then((res) => {
                    if (res === 1) {
                        this.$message.success("还书成功");
                        this.handleCurrentChange(this.queryParam.page);
                    } else if (res === 0) {
                        this.$message.error("该图书已经是归还的状态");
                    } else {
                        this.$message.error("还书失败");
                    }
                });
            });
        },

        // 批量还书
        // handleReturn(row, index) {
        //   this.$confirm('确定要还书吗?', '提示', {
        //     confirmButtonText: '确定',
        //     cancelButtonText: '取消',
        //     type: 'warning'
        //   }).then(() => {
        //     returnBook(row.borrowId, row.bookId).then(res => {
        //       if(res === 1) {
        //         this.$message.success('还书成功')
        //         this.handleCurrentChange(this.queryParam.page)
        //       } else {
        //         this.$message.error('还书失败')
        //       }
        //     })
        //   })
        // },
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
                userId: null,
                userName: null,
                bookName: null,
            },
        };
    },
    computed: {
        // 获得user信息
        ...mapGetters(["id", "name", "roles"]),
        roleisAdmin() {
            if (this.roles[0] === "admin") return true;
            else return false;
        },
    },
    watch: {
        "queryParam.userId": {
            immediate: true,
            handler() {
                if (this.roleisAdmin) {
                    this.queryParam.userId = null;
                } else {
                    this.queryParam.userId = this.id;
                }
            },
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
