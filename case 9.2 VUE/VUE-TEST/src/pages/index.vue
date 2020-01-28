<template>

  <p>index in , 谢谢</p>

</template>
<script>
  export default {
    name: 'index',
    data() {
      return {
        itemId: null, //题目ID
        choosedNum: null, //选中答案索引
        choosedId: null //选中答案id
      }
    },
    props: ['index'],
    methods: {},
    created() {
      console("this is in create");
    }
  }
</script>
