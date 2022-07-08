Vue.component("edit-offer", {
    data: function () {
        return {
            offer: {},
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
            validTrainingType: false,
            changeTrainerFlag: false
        }
    },
    template: ` 
<div>
    <h1>Create offer</h1>
    <table>
    <tr>
    <td>Name: </td>
    <td><input type="text" v-model = "offer.name"> <div id="spanName" style="visibility: hidden">Cannot be empty</div></td>
</tr>
<tr>
    <td>Type: </td>
    <td><select name="grade" v-model="offer.type" v-on:change="changeType">
      <option value="TRAINING">Training</option>
      <option value="SAUNA">Sauna</option>
      <option value="SWIMMING_POOL">Swimming pool</option>
    </select> <div id="spanType" style="visibility: hidden">Must choose something</div></td></td>
</tr>
<tr v-if="offer.type == 'TRAINING'">
    <td>Trainer: </td>
   
    <td>
     <p v-if="!changeTrainerFlag">{{offer.trainer.name}} {{offer.trainer.surname}} | {{offer.trainer.username}}</p>
    <select v-model="offer.trainer" v-else>
          <option v-for="trainer in trainers" :value="trainer">{{trainer.name}} {{trainer.surname}} | {{trainer.username}}</option>
        </select><div id="spanTrainer" style="visibility: hidden">Must choose trainer</div>
            <button v-on:click="changeTrainer" v-if="!changeTrainerFlag">Change trainer</button>
        </td>
</tr>
<tr v-if="offer.type == 'TRAINING'">
    <td>Training type: </td>
    <td>
    
    <select v-model="offer.trainingType" >
          <option value="GROUP">Group</option>
          <option value="PERSONAL">Personal</option>
          <option value="GYM">Gym</option>
    </select>
         <div id="spanTrainingType" style="visibility: hidden">Must choose training type</div></td>
</tr>
<tr>
    <td>Hours duration: </td>
    <td><input type="text" v-model = "offer.hourDuration"></td>
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
</table>
    <button v-on:click="edit">Edit</button>
</div>		  
`
    ,
    methods: {
        edit() {

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
                axios.post('/manager/get-facility/offer/edit', this.offer, this.configHeaders)
                    .then(response => {
                        if(response.data === false) {
                            alert('Vec postoji sa tim imenom')
                        } else {
                            router.push("/open-facility")
                        }
                    })
            }
        },
        changeTrainer() {
            this.changeTrainerFlag = true
        },
        changeType() {
            if(this.offer.type === "TRAINING") {
                this.changeTrainerFlag = true
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
        axios.get('/manager/get-facility/offer?id='+this.$route.params.id, this.configHeaders)
            .then(response => {
                this.offer = response.data
                if(this.offer == null) {
                    router.push("/open-facility")
                }
            })
    },
});