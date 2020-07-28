<template>
  
</template>

<script>
import axios from 'axios';
import SERVER from '../../api/base'
export default {
    created() {
        axios.get(SERVER.BASE + '좋아요한사람들명단')
        .then(res =>
        this.likedUsers = res.data.likedUsers
        )
        .catch(err =>
        console.errer(err))
    },
    

}
</script>

<style>

</style>