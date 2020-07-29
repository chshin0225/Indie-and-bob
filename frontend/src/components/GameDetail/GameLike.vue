<template>
<p>ddfd</p>
</template>

<script>
import axios from "axios";
import SERVER from "../../api/base";
export default {
  props: ['project'],
  created() {
    axios
      .get(SERVER.BASE + SERVER.LIKEBYGAME + this.project.gameId)
      .then((res) => (this.likedUsers = res.data.likedUsers))
      .catch((err) => console.errer(err));
  },
  data() {
      return {
          likeDialog :false
      }
  }
};
</script>

<style>
</style>