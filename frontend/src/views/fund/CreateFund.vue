<template>
  <v-container>
    <div v-if="rewardFetched && userDataFetched">
      <h1 class="text-center">리워드 구매</h1>

      <!-- 리워드 정보 -->
      <v-row class="justify-center">
        <v-col sm=8> 
          <h2 class="pb-1">리워드 정보</h2>
          <v-card class="mx-auto" outlined>
            <div class="overline px-4 pt-3">프로젝트 이름</div>
            <v-card-title class="headline pt-1">{{ reward.rewardName }}</v-card-title>
            <v-card-text>{{ reward.content }}</v-card-text>
            <v-card-text class="subtitle-1 text-right pt-0 black--text">{{ reward.price }} 원</v-card-text>
          </v-card>
        </v-col>
      </v-row>

      <!-- 결제자 정보 -->
      <v-row class="justify-center">
        <v-col sm=8>
          <h2 class="pb-1">구매 정보 입력</h2>
          <v-divider class="pb-3"></v-divider>
        </v-col>
      </v-row>

      <!-- 이름 -->
      <v-row class="justify-center">
        <v-col class="py-0" sm="8">
          <label for="name">구매자 이름</label>
          <v-text-field
            ref="name"
            v-model="userInfo.name"
            id="name"
            placeholder="이름을 써주세요"
            type="text"
            hide-details="true"
            outlined
            required
          ></v-text-field>
        </v-col>
      </v-row>
      <!-- <v-btn @click='myAddress'>내 기본 주소 사용</v-btn> -->

      <!-- daum address API -->
      <v-row
        ref="searchWindow"
        :style="searchWindow"
        style="border:1px solid;width:100%;margin:5px 0;position:relative"
        class="justify-center"
      >
        <v-col class="py-0" sm="8">
          <img
            src="//t1.daumcdn.net/postcode/resource/images/close.png"
            id="btnFoldWrap"
            style="cursor:pointer;position:absolute;right:0px;top:-1px;z-index:1"
            @click="searchWindow.display = 'none'"
            alt="close"
          />
        </v-col>
      </v-row>

      <!-- 우편번호 -->
      <v-row class="justify-center mt-2">
        <v-col class="py-0 mt-3" sm="4">
          <label for="postcode">우편번호</label>
          <v-text-field
            v-model="userInfo.postcode"
            outlined
            hide-details="true"
            id="postcode"
            placeholder="자동으로 입력됩니다"
            required
          ></v-text-field>
        </v-col>
        <v-col class="d-flex align-end" sm="4">
          <v-btn @click="execDaumPostcode" depressed color="accent">우편번호 찾기</v-btn>
        </v-col>
      </v-row>

      <!-- 주소 -->
      <v-row class="justify-center my-4">
        <v-col class="py-0" sm="8">
          <label for="address">배송 주소</label>
          <v-text-field
            type="text"
            v-model="userInfo.address"
            outlined
            hide-details="true"
            id="address"
            placeholder="자동으로 입력됩니다"
            required
          ></v-text-field>
        </v-col>
      </v-row>

      <!-- 상세주소 -->
      <v-row class="justify-center my-4">
        <v-col class="py-0" sm="8">
          <label for="extraAddress">상세 주소</label>
          <v-text-field
            ref="extraAddress"
            v-model="userInfo.extraAddress"
            id="extraAddress"
            placeholder="상세 주소를 입력해주세요"
            type="text"
            hide-details="true"
            outlined
            required
          ></v-text-field>
        </v-col>
      </v-row>

      <!-- 전화번호 -->
      <v-row class="justify-center my-4">
        <v-col class="py-0" sm="8">
          <label for="phonenumber">핸드폰 번호 ('-' 제외)</label>
          <v-text-field
            v-model="userInfo.phoneNumber"
            id="phonenumber"
            placeholder="핸드폰 번호를 입력해주세요"
            type="text"
            hide-details="true"
            outlined
            required
          ></v-text-field>
        </v-col>
      </v-row>

      <!-- 결제 btn -->
      <v-row class="justify-center">
        <v-col sm=8 class="text-right">
          <v-btn @click="kakaoPay()" color="accent" depressed>결제하기</v-btn>
        </v-col>
      </v-row>
    </div>

  </v-container>
</template>

<script>
import axios from "axios"
import SERVER from '../../api/base'
import { mapActions, mapGetters, mapState } from "vuex"

export default {
  name: 'CreateFund',

  data() {
    return {
      reward: null,
      searchWindow: {
        display: "none",
        height: "300px"
      },
    };
  },

  computed: {
    ...mapState(["userInfo",]),
    ...mapGetters(['headersConfig', 'userDataFetched']),

    rewardFetched: function() {
      return !!this.reward
    },
  },

  methods: {
    ...mapActions(["getUserInfo"]),

    getReward(gameId) {
      axios.get(SERVER.BASE + SERVER.REWARDDETAIL + gameId, this.headersConfig)
        .then(res => {
          // console.log(res.data.object)
          this.reward = res.data.object;
        })
        .catch(err => console.error(err));
    },

    kakaoPay() {
      const PARAMS = {
        "rewardId": this.reward.rewardId,
        "gameId": this.reward.gameId,
        "money": this.reward.price,
      }
      axios.post(SERVER.BASE + SERVER.KAKAOPAY, PARAMS, this.headersConfig)
        .then(res => {
          const QRCode = res.data.data
          // window.open(QRCode, "_blank")
          window.location.href = QRCode
        })
        .catch(err => console.error(err))
    },

    execDaumPostcode() {
      const currentScroll = Math.max(
        document.body.scrollTop,
        document.documentElement.scrollTop
      );

      // eslint-disable-next-line
      new daum.Postcode({
        onComplete: data => {
          if (data.userSelectedType === "R") {
            this.userInfo.address = data.roadAddress;
          } else {
            this.userInfo.address = data.jibunAddress;
          }
          if (data.userSelectedType === "R") {
            if (data.bname !== "" && /[동|로|가]$/g.test(data.bname)) {
              this.userInfo.extraAddress = data.bname;
            }
            if (data.buildingName !== "" && data.apartment === "Y") {
              this.userInfo.extraAddress +=
                this.userInfo.extraAddress !== ""
                  ? `, ${data.buildingName}`
                  : data.buildingName;
            }
            if (this.userInfo.extraAddress !== "") {
              this.userInfo.extraAddress = ` (${this.userInfo.extraAddress})`;
            }
          } else {
            this.userInfo.extraAddress = "";
          }
          this.userInfo.postcode = data.zonecode;
          this.searchWindow.display = "none";
          document.getElementById('extraAddress')[0].focus()
          document.body.scrollTop = currentScroll;
        },
        onResize: size => {
          this.searchWindow.height = `${size.height}px`;
        },
        width: "100%",
        height: "100%"
      }).embed(this.$refs.searchWindow);
      this.searchWindow.display = "block";
    },
  },

  created() {
    let username = localStorage.getItem('username')
    this.getUserInfo(username)
    this.getReward(this.$route.params.id)
  },

};
</script>

<style>
</style>