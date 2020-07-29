<template>
  <v-row justify="center">
    <v-col cols="12">
      <v-row class="justify-center">
        <v-col cols="12" sm="10">
          <v-text-field
            label="comment"
            v-model="communityComment"
            id="communityComment"
            outlined
            required
            hide-detail="true"
            placeholder="한줄응원을 입력해주세요."
          ></v-text-field>
        </v-col>
        <v-col cols="12" sm="2">
          <v-btn primary @click="onWriteButton">글쓰기</v-btn>
        </v-col>
      </v-row>
      <v-divider></v-divider>
      <v-list two-line>
        <template v-for="article in community">
          <v-list-item :key="article.id">
            <v-list-item-avatar>
              <img :src="article.user.profileurl" />
            </v-list-item-avatar>
            <v-list-item-content>
              <v-row v-if="article.user===localStorage.getItem('username')">
                <v-col cols="11">
                  <v-list-item-title>{{article.content}}</v-list-item-title>
                </v-col>
                <v-col cols="1">
                  <v-btn @click="communityCommentDelete(article.commentId)" x-small outlined>X</v-btn>
                </v-col>
              </v-row>
              <v-row v-else>
                <v-col cols="11">
                  <v-list-item-title>{{article.content}}</v-list-item-title>
                </v-col>
              </v-row>

              <v-list-item-subtitle>{{article.createdAt}} by {{article.user}}</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </template>.
      </v-list>
    </v-col>
  </v-row>
</template>

<script>
import axios from "axios";
import SERVER from "../../api/base";
export default {
  name: "GameCommunity",
  created() {
    axios
      .get(SERVER.BASE + "gamecommunityurl")
      .then((res) => {
        this.community = res.data.object;
      })
      .catch((err) => console.error(err));
  },
  data() {
    return {
      communityComment: "",
    };
  },
  methods: {
    onWriteButton() {
      let PARAMS = {
        nickname: localStorage.getItem("username"),
        content: this.communityComment,
      };
      axios
        .post(SERVER.BASE, PARAMS, this.headersConfig)
        .then((res) => {
          console.log(res.data);
          axios
            .get(SERVER.BASE + "gamecommunityurl")
            .then((res) => {
              this.community = res.data.object;
            })
            .catch((err) => console.error(err));
        })
        .catch((err) => console.error(err.data));
    },
    communityCommentDelete(ID) {
      axios
        .delete(SERVER.BASE + ID, this.headersConfig)
        .then((res) => {
          console.log(res.data);
          axios
            .get(SERVER.BASE + "gamecommunityurl")
            .then((res) => {
              this.community = res.data.object;
            })
            .catch((err) => console.error(err));
        })
        .catch((err) => console.error(err.data));
    },
  },
};
</script>

<style>
</style>