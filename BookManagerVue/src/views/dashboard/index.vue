<!--
 * @Description: 
 * @Author: 
 * @Date: 2024-02-21 18:13:21
-->
<!--
 * @Description: 
 * @Author: 
 * @Date: 2024-02-21 18:13:21
-->
<template>
    <div class="dashboard-container">
        <div
            title="欢迎页面"
            style="
                padding: 20px 50px 35px 50px;
                overflow: hidden;
                color: black;
                font: lighter 34px 'Lucida Sans Unicode';
            "
            id="index-title"
            data-options="iconCls:'icon-heart',plain:true"
        >
            <b>
                欢迎使用图书管理系统
            </b>
            <hr />

            <img :src="require('@/assets/index.jpg')" style="width: 78%" />
        </div>
    </div>
</template>

<script>
import { mapGetters } from "vuex";


export default {
    name: "Dashboard",
    computed: {
        ...mapGetters(["id", "name", "roles"]),
    },
    mounted() {
        
    }
};
</script>
