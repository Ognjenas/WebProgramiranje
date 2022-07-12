Vue.component("create-offer", {
    data: function () {
        return {
            offer: { name: "", type: "", trainingType: "", trainerId: -1},
            userInfo: {
                username: "",
                role: ""
            },
            configHeaders: {
                headers: {
                    token: $cookies.get("token"),
                }
            },
            trainers: {},
            validName: false,
            validType: false,
            validTrainer: false,
            validTrainingType: false
        }
    },
    template: ` 
<div>
<div class="facility-list-container">
    <h1>Create offer</h1>
    <table  class="show-facilities-table" >
    <tr>
    <td>Name: </td>
    <td><input type="text" v-model = "offer.name"> <div id="spanName" style="visibility: hidden">Cannot be empty</div></td></td>
</tr>
<tr>
    <td>Type: </td>
    <td><select name="grade" v-model="offer.type">
      <option value="TRAINING">Training</option>
      <option value="SAUNA">Sauna</option>
      <option value="SWIMMING_POOL">Swimming pool</option>
    </select> <div id="spanType" style="visibility: hidden">Must choose something</div></td></td>
</tr>
<tr v-if="offer.type == 'TRAINING'">
    <td>Trainer: </td>
    <td><select v-model="offer.trainerId">
          <option v-for="trainer in trainers" :value="trainer.id">{{trainer.name}} {{trainer.surname}} | {{trainer.username}}</option>
        </select><div id="spanTrainer" style="visibility: hidden">Must choose trainer</div></td></td>
</tr>
<tr v-if="offer.type == 'TRAINING'">
    <td>Training type: </td>
    <td><select v-model="offer.trainingType">
          <option value="GROUP">Group</option>
          <option value="PERSONAL">Personal</option>
          <option value="GYM">Gym</option>
        </select> <div id="spanTrainingType" style="visibility: hidden">Must choose training type</div></td></td>
</tr>
<tr>
    <td>Hours duration: </td>
    <td><input type="text" v-model = "offer.hourDuration"></td></td>
</tr>
<tr>
    <td>Minutes duration: </td>
    <td><input type="text" v-model = "offer.minuteDuration"></td>
</tr>

<tr>
    <td>Description: </td>
    <td><input type="text" v-model = "offer.description"></td>
</tr>
<tr>
    <td>Price: </td>
    <td><input type="text" v-model = "offer.price"></td>
    </tr>
    <tr>
    <td>Image: </td>
    <td><input id="fileOffer" type="file" onchange="encodeImageFileAsURLOffer()" accept="image/*">
       
        <input type="hidden" id="pictureOffer"></td>
</tr>
<tr>
    <td colspan="2"> <button v-on:click="create" class="login-button">Create</button></td>
</tr>
</table>
</div>
   
</div>		  
`
    ,
    methods: {
        create() {

            if(this.offer.name === "") {
                this.validName = false;
                document.getElementById("spanName").style.visibility = "visible"
                document.getElementById("spanName").style.color = "red"
            } else {
                this.validName = true;
                document.getElementById("spanName").style.visibility = "hidden"
            }

            if(this.offer.type === "") {
                this.validType = false;
                document.getElementById("spanType").style.visibility = "visible"
                document.getElementById("spanType").style.color = "red"
            } else {
                this.validType = true;
                document.getElementById("spanType").style.visibility = "hidden"
            }

            if(this.offer.type === "TRAINING") {
                if(this.offer.trainingType === "") {
                    this.validTrainingType = false;
                    document.getElementById("spanTrainingType").style.visibility = "visible"
                    document.getElementById("spanTrainingType").style.color = "red"
                } else {
                    this.validTrainingType = true;
                    document.getElementById("spanTrainingType").style.visibility = "hidden"
                }

                if(this.offer.trainerId === -1) {
                    this.validTrainer = false;
                    document.getElementById("spanTrainer").style.visibility = "visible"
                    document.getElementById("spanTrainer").style.color = "red"
                } else {
                    this.validTrainer = true;
                    document.getElementById("spanTrainer").style.visibility = "hidden"
                }
            } else {
                this.validTrainingType = true;
                this.validTrainer = true;
            }

            if(this.validName === true && this.validType === true && this.validTrainingType === true && this.validTrainer === true){
                this.offer.imgSource = document.getElementById("pictureOffer").value;
                axios.post('/manager/create-offer', this.offer, this.configHeaders)
                    .then(response => {
                        if(response.data === false) {
                            alert('Vec postoji sa tim imenom')
                        } else {
                            router.push("/open-facility")
                        }
                    }).catch(function (error) {
                    alert('Vec postoji sa tim imenom')
                })
            }
        }


    },

    mounted() {
        if ($cookies.get("token") == null) {
            router.push("/login")
        }
        axios.post('users/get-info', $cookies.get("token"), this.configHeaders)
            .then(response => {
                this.userInfo = response.data
                if (this.userInfo.role !== 'MANAGER') {
                    router.push("/")
                }
            })
        axios.get('/manager/create-offer/get-all-trainers', this.configHeaders)
            .then(response => {
                this.trainers = response.data.trainers
            })
    },
});