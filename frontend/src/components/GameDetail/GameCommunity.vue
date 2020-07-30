<template>
  <v-row v-if="commentRender" justify="center">
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
      <v-list v-if="community" two-line>
        <template v-for="article in community">
          <v-list-item :key="article.id">
            <v-list-item-content>
              <v-row v-if="article.nickname===myName">
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

              <v-list-item-subtitle>{{article.createdAt}} by {{article.nickname}}</v-list-item-subtitle>
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
  props: ['project'],
  name: "GameCommunity",
  mounted() {
    console.log(this.project)
    axios
      .get(SERVER.BASE + SERVER.GAMECOMMUNITY +'/'+ this.project.gameId)
      .then((res) => {
        this.community = res.data.object;
        this.commentRender = true;
      })

      .catch((err) => console.error(err));
  },
  data() {
    return {
      communityComment: "",
      myName: localStorage.getItem('username'),
      commentRender : false,
    };
  },
  methods: {
    onWriteButton() {
      let PARAMS = {
        nickname: localStorage.getItem("username"),
        content: this.communityComment,
        gameId : this.project.gameId
      };
      axios
        .post(SERVER.BASE+SERVER.GAMECOMMUNITY, PARAMS, this.headersConfig)
        .then((res) => {
          console.log(res.data);
          this.communityComment = '',
          axios
            .get(SERVER.BASE + SERVER.GAMECOMMUNITY + '/'+ this.project.gameId)
            .then((res) => {
              console.log(res.data.object)
              this.community = res.data.object;
            })
            .catch((err) => console.error(err));
        })
        .catch((err) => console.error(err.data));
    },
    communityCommentDelete(ID) {
      axios
        .delete(SERVER.BASE+SERVER.GAMECOMMUNITY+'/' + ID, this.headersConfig)
        .then((res) => {
          console.log(res.data);
          axios
            .get(SERVER.BASE + SERVER.GAMECOMMUNITY + '/'+ this.project.gameId)
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