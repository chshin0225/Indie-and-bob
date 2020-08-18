<template>
  <v-container>
    <h1 class="text-center">회원정보 변경</h1>
    
    <!-- id -->
    <v-row class="justify-center mt-4 mb-2">
      <v-col class="py-0" sm="6">
        <p>닉네임 : {{ nickname }}</p>
      </v-col>
    </v-row>

    <!-- email -->
    <v-row class="justify-center mt-2 mb-4">
      <v-col class="py-0" sm="6">
        <p>이메일 : {{ email }}</p>
      </v-col>
    </v-row>

    <!-- name -->
    <v-row class="justify-center my-4">
      <v-col class="py-0" sm="6">
        <label for="name">이름</label>
        <v-text-field
          hide-details="true"
          v-model="name"
          id="name"
          outlined
          placeholder="이름을 입력해주세요."
          type="text"
        />
      </v-col>
    </v-row>

    <v-row class="justify-center my-4">
      <v-col class="py-0" sm="6">
        <label for="usertype">좋아하는 게임 장르</label>
        <v-select
          hide-details="true"
          :items="genres"
          id="genres"
          placeholder="본인이 좋아하는 게임 장르를 선택해주세요. (복수선택 가능)"
          v-model="genre"
          outlined
          multiple
          chips
        ></v-select>
        <small class="d-block primary--text" v-if="error.genre">{{ error.genre }}</small>
      </v-col>
    </v-row>

    <v-row class="justify-center my-4">
      <v-col class="py-0" sm="6">
        <label for="usertype">주 사용자 유형</label>
        <v-select
         hide-details="true" 
         :items="userTypes" 
         id="usertype" 
         v-model="usertype" 
         outlined
        ></v-select>
      </v-col>
    </v-row>

    <!-- bank account -->
    <v-row class="justify-center my-4" v-if="usertype === '개발자'">
      <v-col class="py-0" sm="2">
        <label for="bankname">은행명</label>
        <v-select
          hide-details="true"
          :items="banks"
          id="bankname"
          v-model="bankname"
          outlined
        ></v-select>
      </v-col>
      <v-col class="py-0" sm="4">
        <label for="accountnumber">계좌번호</label>
        <v-text-field
          hide-details="true"
          v-model="accountnumber"
          id="accountnumber"
          outlined
          placeholder="(-)를 제외한 숫자만 입력해주세요"
        />
        <small class="d-block primary--text" v-if="error.accountnumber">{{ error.accountnumber }}</small>
      </v-col>
    </v-row>

    <!-- profile image -->
    <v-row class="justify-center my-4">
      <v-col class="py-0" sm="6">
        <label for="profile-image">이미지</label>
        <v-file-input
          accept="image/*"
          hide-details="true"
          v-model="profileImage"
          id="profile-image"
          outlined
          placeholder="프로필 사진을 등록해주세요."
        />
      </v-col>
    </v-row>

    <!-- original profile image -->
    <v-row v-if="originalProfile" class="justify-center">
      <v-col class="py-0" sm="6">
        <label for="profile-image" class="font-weight-bold">기존에 사용하던 이미지</label>
        <v-img contain :src="originalProfile" :alt="nickname" class="mt-2"></v-img>
      </v-col>
    </v-row>

    <!-- introduction -->
    <v-row class="justify-center my-4">
      <v-col class="py-0" sm="6">
        <label for="introduction">한 줄 소개</label>
        <v-text-field
          hide-details="true"
          v-model="introduction"
          id="introduction"
          outlined
          placeholder="자신을 소개해주세요~"
          type="text"
        />
        <!-- <small class="d-block" v-if="error.email">{{ error.email }}</small> -->
      </v-col>
    </v-row>

    <!-- phone number -->
    <v-row class="justify-center my-4">
      <v-col class="py-0" sm="6">
        <label for="phonenumber">핸드폰 번호('-' 제외)</label>
        <v-text-field
          hide-details="true"
          v-model="phonenumber"
          id="phonenumber"
          outlined
        />
        <small class="d-block" v-if="error.phonenumber">{{ error.phonenumber }}</small>
      </v-col>
    </v-row>

    <!-- address -->
    <v-row
      ref="searchWindow"
      :style="searchWindow"
      class="my-4"
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

    <v-row class="justify-center my-4">
      <v-col class="py-0" sm="3">
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
      <v-col class="my-3 py-0 d-flex align-end" sm="3">
        <v-btn @click="execDaumPostcode" depressed color="accent">우편번호 찾기</v-btn>
      </v-col>
    </v-row>

    <v-row class="justify-center my-4">
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

    <v-row class="justify-center my-4">
      <v-col class="py-0" sm="6">
        <label for="extraAddress">상세주소</label>
        <v-text-field
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

    <!-- term -->
    <v-row class="justify-center my-4">
      <v-col sm="6">
        <v-row class="justify-space-between">
          <!-- <v-col class="py-0">
            <v-dialog v-model="dialog" persistent max-width="290">
              <template v-slot:activator="{ on, attrs }">
                <v-btn text v-bind="attrs" v-on="on" color="primary">약관보기</v-btn>
              </template>
              <v-card>
                <v-card-title class="headline">IndieAndBob 약관</v-card-title>
                <v-card-text>게임을 게임ㅁ게임 밥알을 밥알바알</v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="green darken-1" text @click="dialog = false">닫기</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </v-col> -->

          <v-col class="text-right py-0">
            <v-btn
              @click="changeUserInfo(
                {
                  name: name, 
                  email: email, 
                  genreId: genre, 
                  profile: profileImage, 
                  profileURL: profileURL, 
                  password: password, 
                  nickname: nickname, 
                  developer: is_developer, 
                  phoneNumber: phonenumber, 
                  bankName: bankname, 
                  bankAccount: accountnumber, 
                  postcode: postcode, 
                  address: address, 
                  extraAddress: extraAddress, 
                  introduction: introduction
                })"
              :disabled="!isSubmit"
              class="d-inline-block"
              :class="{disabled : !isSubmit}"
              depressed
              large
              color="accent"
            >회원 정보 변경</v-btn>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from "axios"
import { mapActions, mapState } from "vuex"
import firebase from "firebase"
import SERVER from "../../api/base";

export default {
  name: 'Edit',

  created() {
    console.log('1')
    this.component = this
    console.log('2')
    this.getUser()
  },

  data() {
    return {
      email: "",
      password: "",
      profileImage: null,
      originalProfile: null,
      profileURL: null,
      phonenumber: "",
      nickname: "",
      genre: [],
      name: "",
      usertype: "",
      userTypes: ["일반 사용자", "개발자"],
      is_developer: false,
      banks: [
        "하나", 
        "우리", 
        "국민", 
        "농협", 
        "카카오뱅크", 
        "신한", 
        "IBK기업", 
        "SC제일", 
        "씨티",
        "대구",
        "부산",
        "광주",
        "새마을",
        "경남",
        "전북",
        "제주",
        "산업",
        "우체국",
        "신협",
        "수협",
        "케이뱅크"
      ],
      bankname: "",
      accountnumber: "",
      introduction: "",
      isLoading: false,
      error: {
        email: false,
        phonenumber: false,
        nickName: "",
        accountnumber: false,
        genre: false,
      },
      isSubmit: false,
      termPopup: false,
      dialog: false,
      searchWindow: {
        display: "none",
        height: "300px"
      },
      postcode: "",
      address: "",
      extraAddress: ""
    };
  },

  computed: {
    ...mapState(['userInfo', "genres"])
  },

  watch: {
    genre: function() {
      this.checkForm();
    },
    phonenumber: function() {
      this.checkForm();
    },
    profileImage() {
      this.originalProfile = null
    },
    usertype: function () {
      if (this.usertype === "개발자") {
        this.is_developer = true;
      } else {
        this.is_developer = false;
      }
    },
  },

  methods: {
    ...mapActions(['changeUserInfo']),

    getUser() {
      console.log('getuser')
      axios
        .get(SERVER.BASE + SERVER.USERINFO + `/${this.$route.params.username}`)
        .then(res => {
          console.log(res)
          this.email = res.data.object.email;
          this.password = res.data.object.password;
          this.name = res.data.object.name;
          this.introduction = res.data.object.introduction;
          this.phonenumber = res.data.object.phoneNumber;
          this.nickname = res.data.object.nickname;
          // res.data.object.genreName.forEach(item => {
          //   this.genre.push(this.idToGenre[item])
          // })
          this.genre = res.data.object.genreName
          console.log(this.genre)
          this.is_developer = res.data.object.developer;
          if (res.data.object.developer) {
            this.usertype = "개발자"
          } else {
            this.usertype = "일반 사용자"
          }
          this.bankname = res.data.object.bankName;
          this.accountnumber = res.data.object.bankAccount;
          this.postcode = res.data.object.postcode;
          this.address = res.data.object.address;
          this.extraAddress = res.data.object.extraAddress;
          if (res.data.profile !== null) {
            const storageRef = firebase.storage().ref()
            storageRef.child(res.data.object.profile).getDownloadURL().then(url => {
            this.originalProfile = url
            }) 
          }
          this.profileURL = res.data.object.profile
        })
        .catch(err => console.error(err));
    },

    checkForm() {
      if (this.genre.length <= 0)
        this.error.genre = "최소 1개의 장르를 선택해야합니다.";
      else this.error.genre = false;

      if ((this.phonenumber + "").length != 11)
        this.error.phonenumber = "올바른 휴대폰번호 형식이ㅣ 아닙니다.";
      else this.error.phonenumber = false;

      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
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
          this.$refs.extraAddress.focus();
          this.searchWindow.display = "none";
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
  },


};
</script>

