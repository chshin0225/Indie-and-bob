<template>
  <v-container>
    <h2>리워드 구매</h2>
    <h4>{{reward.title}}</h4>
    <p>{{reward.content}}</p>
    <p>{{reward.price}}원</p>

    <h3>구매정보입력</h3>
    <v-row class="justify-center">
      <v-col class="py-0" sm="6">
        <label for="name">구매자 이름</label>
        <v-text-field
          ref="name"
          v-model="name"
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
      <!-- address -->
    <v-row
      ref="searchWindow"
      :style="searchWindow"
      style="border:1px solid;width:100%;margin:5px 0;position:relative"
    >
      <v-col class="py-0" sm="6">
        <img
          src="//t1.daumcdn.net/postcode/resource/images/close.png"
          id="btnFoldWrap"
          style="cursor:pointer;position:absolute;right:0px;top:-1px;z-index:1"
          @click="searchWindow.display = 'none'"
          alt="close"
        />
      </v-col>
    </v-row>

    <v-row class="justify-center">
      <v-col class="py-0 mt-3" sm="3">
        <label for="postcode">우편번호</label>
        <v-text-field
          v-model="postcode"
          outlined
          hide-details="true"
          id="postcode"
          placeholder="자동으로 입력됩니다."
          required
        ></v-text-field>
      </v-col>
      <v-col class="my-2" sm="3">
        <v-btn @click="execDaumPostcode" dark>우편번호 찾기</v-btn>
      </v-col>
    </v-row>
    <v-row class="justify-center">
      <v-col class="py-0" sm="6">
        <label for="address">주소</label>
        <v-text-field
          type="text"
          v-model="address"
          outlined
          hide-details="true"
          id="address"
          placeholder="자동으로 입력됩니다"
          required
        ></v-text-field>
      </v-col>
    </v-row>
    <v-row class="justify-center">
      <v-col class="py-0" sm="6">
        <label for="extraAddress">상세주소</label>
        <v-text-field
          ref="extraAddress"
          v-model="extraAddress"
          id="extraAddress"
          placeholder="상세주소를 입력해주세요"
          type="text"
          hide-details="true"
          outlined
          required
        ></v-text-field>
      </v-col>
    </v-row>
    <v-row justify="center">
      <v-col class="py-0" sm="6">
        <label for="phonenumber">전화 번호</label>
        <v-text-field
          v-model="phonenumber"
          id="phonenumber"
          type="number"
          hide-details="true"
          outlined
          required
        ></v-text-field>
      </v-col>
    </v-row>
    <v-row justify="center">
      <v-col col="auto">
        <v-btn color="primary">구매하기</v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
// import { mapState } from "vuex";
// import axios from "axios";
// import SERVER from '../../api/base'

export default {
  // created() {
  //   this.getUserInfo();
  //   axios
  //     .get(SERVER.BASE + SERVER.REWARDDETAIL + this.$route.params.id)
  //     .then(res => {
  //       this.reward = res.data;
  //     })
  //     .error(err => console.error(err));
  // },

  methods: {
    // ...mapActions(["getUserInfo"]),

    execDaumPostcode() {
      const currentScroll = Math.max(
        document.body.scrollTop,
        document.documentElement.scrollTop
      );
      // eslint-disable-next-line
      new daum.Postcode({
        onComplete: data => {
          if (data.userSelectedType === "R") {
            this.address = data.roadAddress;
          } else {
            this.address = data.jibunAddress;
          }
          if (data.userSelectedType === "R") {
            if (data.bname !== "" && /[동|로|가]$/g.test(data.bname)) {
              this.extraAddress += data.bname;
            }
            if (data.buildingName !== "" && data.apartment === "Y") {
              this.extraAddress +=
                this.extraAddress !== ""
                  ? `, ${data.buildingName}`
                  : data.buildingName;
            }
            if (this.extraAddress !== "") {
              this.extraAddress = ` (${this.extraAddress})`;
            }
          } else {
            this.extraAddress = "";
          }
          this.postcode = data.zonecode;
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
    }
    // myAddress() {
    //   this.postcode= this.user.postcode
    //   this.address = this.user.address
    //   this.extraAddress = this.user.extraAddress
    // }
  },
  // computed: {
  //   ...mapState(["user"])
  // },
  data() {
    return {
      reward: {
        title: "example",
        content: "example content",
        price: 10000,
      },
      address: "",
      searchWindow: {
        display: "none",
        height: "300px"
      },
      postcode: "",
      extraAddress: "",
      phonenumber: "",
      name : '',
    };
  },
};
</script>

<style>
</style>