Vue.component("facility-create", {
    data: function () {
        return {
            form: {
                name: "",
                type: "",
                city: "",
                street: "",
                strNum: "",
                postCode: "",
                geoWidth: "",
                geoLength: "",
                managerId: "",
                imgSource: "",
                workdayHours:"",
                saturdayHours:"",
                sundayHours:"",
            },
            manager: {
                managerName: "",
                managerSurname: "",
                managerUsername: "",
                managerPassword: "",
                managerGender: "",
                managerBirthDate: "",
                name: "",
                type: "",
                city: "",
                street: "",
                strNum: "",
                postCode: "",
                geoWidth: "",
                geoLength: "",
                imgSource: "",
                workdayHours:"",
                saturdayHours:"",
                sundayHours:"",
            },
            fromWorking:"",
            toWorking:"",
            fromSaturday:"",
            toSaturday:"",
            fromSunday:"",
            toSunday:"",
            selectedBirthDate: "",
            configHeaders: {
                headers: {
                    token: $cookies.get("token"),
                }
            },
            isDisabled: true,
            managerCreationSection: false,
            selectedFile: null,
            managerList: null,
        }
    },
    template: ` 
<div>
<div class="facility-list-container">
    <h1>Facility Create</h1>
    <form method="post">
    
    <table class="show-facilities-table">
    <td>
        <table>
        <tr>
        <td><label>Name</label></td>
        <td><input id="name" name="name" v-model="form.name" v-on:input="validate()" placeholder="Enter Facility Name"></td>
        </tr>
        
        <tr>
        <td><label>Type</label></td>
        <td><select id="type" name="type" v-model="form.type" v-on:change="validate()" >
          <option value="">Select Facility Type</option>
          <option value="GYM">GYM</option>
          <option value="FIGHTING_GYM">FIGHTING_GYM</option>
          <option value="SWIMMING_POOL">SWIMMING_POOL</option>
          <option value="SPORTS_CENTER">SPORTS_CENTER</option>
          <option value="DANCE_STUDIO">DANCE_STUDIO</option>
          <option value="STADIUM">STADIUM</option>
        </select></td>
        </tr>
        
        <tr>
        <td><b>Working Hours</b></td>
        </tr>
        <tr>
        <td>WeekDay Open Time</td>
        <td><input id="fromWork" v-model="fromWorking" v-on:input="validate()" placeholder="Enter From Time -> [HH:mm]"></td>
        <td><input id="toWork" v-model="toWorking" v-on:input="validate()" placeholder="Enter To Time -> [HH:mm]"></td>
        </tr>
        
        <tr>
        <td>Saturday Open Time</td>
        <td><input id="fromSat" v-model="fromSaturday" v-on:input="validate()" placeholder="Enter From Time -> [HH:mm]"></td>
        <td><input id="toSat" v-model="toSaturday" v-on:input="validate()" placeholder="Enter To Time -> [HH:mm]"></td>
        </tr>
        
        <tr>
        <td>Sunday Open Time</td>
        <td><input id="fromSun" v-model="fromSunday" v-on:input="validate()" placeholder="Enter From Time -> [HH:mm]"></td>
        <td><input id="toSun" v-model="toSunday" v-on:input="validate()" placeholder="Enter To Time -> [HH:mm]"></td>
        </tr>
        
        <tr>
        <td colspan="3"><label>LOCATION</label></td>
        </tr>
        
        <tr>
        <td><label>City</label></td>
        <td><input id="city" name="city" v-model="form.city" v-on:input="validate()" placeholder="Enter City Name"></td>
        </tr>
        
        <tr>
        <td><label>Street</label></td>
        <td><input id="street" name="street" v-model="form.street" v-on:input="validate()" placeholder="Enter Street Name"></td>
        </tr>
        
        <tr>
        <td><label>Street Number</label></td>
        <td><input id="strNum" name="strNum" v-model="form.strNum" v-on:input="validate()" placeholder="Enter Street Number"></td>
        </tr>
        
        <tr>
        <td><label>Postal Code</label></td>
        <td><input id="postCode" name="postCode" v-model="form.postCode" v-on:input="validate()" placeholder="Enter Postal Code"></td>
        </tr>
        
        
        
        <tr>
        <td><label>Select Image</label></td>
        
        <td colspan="2"><input id="fileFacility" type="file" onchange="encodeImageFileAsURL2()" accept="image/*">
       
        <input type="hidden" id="pictureFacility" style="visibility: hidden"></td>
        </tr>
        
        
        <tr>
        <td>Choose from map</td>
        <td><div id="map23" class="map"></div>  </td>
        </tr>
        
        <tr>
        <td><label>Geographical Length</label></td>
        <td><input id="geoLength" name="geoLength" v-model="form.geoLength" v-on:input="validate()" placeholder="Enter Geographical Length"></td>
        </tr>
        <tr>
        <td><label>Geographical Width</label></td>
        <td><input id="geoWidth" name="geoWidth" v-model="form.geoWidth" v-on:input="validate()" placeholder="Enter Geographical Width"></td>
        </tr>
            
        <tr>
        <td><label>Manager</label></td>
        <td><button class="login-button" type="button" v-if="isListEmpty()" :disabled="managerCreationSection" v-on:click="createManager()" >Create Manager</button>
        <select v-else id="managerCombo" name="managerCombo" v-model="form.managerId" v-on:change="validate()">
          <option value="">Select Manager</option>
          <option v-for="manager in managerList" :value="manager.id">{{manager.name}} {{manager.surname}} | ID:{{manager.id}}</option>
        </select></td>
        </tr>
        
        <tr>
        <td><button class="login-button" type="button" name="createFacilityButton" v-on:click="createFacility()" :disabled="isDisabled" >CreateFacility</button></td>
        <td></td>
        <td><button class="login-button" type="reset" name="resetFormButton" v-on:click="resetForm()">Reset</button></td>
        </tr>
        </table>
       
        
        
    </td>
    
    <td>
        <div name="makeManager" id="makeManager" v-if="managerCreationSection">
        <table>
        <tr>
        <td>Name</td>
        <td><input type="text" v-model="manager.managerName" name="managerName" v-on:change="validate()"></td>
        </tr>
        
        <tr>
        <td>Surname</td>
        <td><input type="text" v-model="manager.managerSurname" name="managerSurname" v-on:change="validate()"></td>
        </tr>
        
        <tr>
        <td>Username:</td>
        <td><input type="text" v-model="manager.managerUsername" name="managerUsername" v-on:change="validate()"></td></tr>
        
        <tr>
        <td>Password: </td>
        <td><input type="password" v-model="manager.managerPassword" name="managerPassword" v-on:change="validate()"></td>
        </tr>
        
        <tr>
        <td>Gender</td>
        <td>
        <label>Male</label><input type="radio" v-model = "manager.managerGender" name="managerGender" value="true" v-on:change="validate()"> <br>
        <label>Female</label><input type="radio" v-model = "manager.managerGender" name="managerGender" value="false" v-on:change="validate()"> 
        </td>
        </tr>
    
        <tr>
        <td>BirthDay</td>
        <td><input type="date" name="managerBirthdate" v-model="selectedBirthDate" v-on:change="validate()"></td>
        </tr>
        </table>
        </div>        	  
    </td>
    </table>
    </form>
    </div>
 </div>	   
    
    
`
    ,
    methods: {
        consoleWrite() {
            this.fillManagerFields();
            console.log(this.form);
            console.log(this.manager);
        },

        createManager() {
            this.managerCreationSection = true;
            this.validate();
        },

        isListEmpty() {
            if (this.managerList == null) return true;
            return false;
        },

        createFacility() {
            if (this.managerCreationSection) {
                this.fillManagerFields();
                const promise = axios.post('/administrator/create-facility-with-manager', this.manager , this.configHeaders);
                promise.then(response => {
                    if (response.data === false) {
                        alert("Manager with same username already exists");
                    }else{

                    }
                    console.log("Create with Manager");
                });
            } else {
                axios.post('/administrator/create-facility', this.form, this.configHeaders);
            }

            router.push('/');
        }

        ,

        fillDate() {
            const trueDate = new Date(this.selectedBirthDate);
            const month = trueDate.getMonth() + 1;
            let text = '{ "year" :' + trueDate.getFullYear() + ', "month" : ' + month + ', "day" : ' + trueDate.getDate() + ' }';
            return JSON.parse(text);
        },

        fileSelected: function (event) {
            this.selectedFile = event.target.files[0];
        }
        ,

        validate() {
            this.form.imgSource = document.getElementById("pictureFacility").value;
            this.form.geoWidth = document.getElementById("geoWidth").value;
            this.form.geoLength = document.getElementById("geoLength").value;
            this.form.workdayHours=this.fromWorking+"-"+this.toWorking;
            this.form.saturdayHours=this.fromSaturday+"-"+this.toSaturday;
            this.form.sundayHours=this.fromSunday+"-"+this.toSunday;
            if (this.managerCreationSection) {
                if (this.form.name === "" || this.form.type === "" || this.form.city === "" || this.form.street === ""
                    || this.form.strNum === "" || this.form.postCode === "" || this.form.geoWidth === "" || this.form.geoLength === ""
                    || this.manager.managerName === "" || this.manager.managerSurname === "" || this.manager.managerUsername === "" || this.manager.managerPassword === ""
                    || this.manager.managerGender === "" || this.selectedBirthDate === "" || this.fromWorking==="" || this.toWorking===""
                    || this.fromSaturday==="" || this.toSaturday==="" || this.fromSunday==="" || this.toSunday==="") {
                    this.isDisabled = true;
                } else {
                    this.isDisabled = false;
                }
            } else {
                if (this.form.name === "" || this.form.type === "" || this.form.city === "" || this.form.street === ""
                    || this.form.strNum === "" || this.form.postCode === "" || this.form.geoWidth === "" || this.form.geoLength === ""
                    || this.form.managerId==="" || this.fromWorking==="" || this.toWorking===""
                    || this.fromSaturday==="" || this.toSaturday==="" || this.fromSunday==="" || this.toSunday==="") {
                    this.isDisabled = true;
                } else {
                    this.isDisabled = false;
                }
            }

        }
        ,

        resetForm() {
            this.form.name = "";
            this.form.type = "";
            this.form.city = "";
            this.form.street = "";
            this.form.strNum = "";
            this.form.postCode = "";
            this.form.geoWidth = "";
            this.form.geoLength = "";
            this.form.managerId = "";
            this.form.imgSource="";
            this.form.workdayHours="";
            this.form.saturdayHours="";
            this.form.sundayHours="";
            this.manager.imgSource ="";
            this.manager.workdayHours="";
            this.manager.saturdayHours="";
            this.manager.sundayHours="";
            this.manager.managerName = "";
            this.manager.managerSurname = "";
            this.manager.managerUsername = "";
            this.manager.managerPassword = "";
            this.manager.managerGender = "";
            this.manager.managerBirthDate = "";
            this.selectedBirthDate = "";

            this.validate();
        }
        ,

        fillManagerFields() {
                this.manager.name=this.form.name;
                this.manager.type=this.form.type;
                this.manager.city=this.form.city;
                this.manager.street=this.form.street;
                this.manager.strNum=this.form.strNum;
                this.manager.postCode=this.form.postCode;
                this.manager.geoWidth=this.form.geoWidth;
                this.manager.geoLength=this.form.geoLength;
                this.manager.managerBirthDate = this.fillDate();
                this.manager.imgSource = this.form.imgSource;
                this.manager.workdayHours= this.form.workdayHours;
                this.manager.saturdayHours=this.form.saturdayHours;
                this.manager.sundayHours= this.form.sundayHours;
        }
    },

    mounted() {


        if ($cookies.get("token") == null) {
            router.push("/login")
        } else {
            axios.post('users/get-info', $cookies.get("token"), this.configHeaders)
                .then(response => {
                    this.userInfo = response.data
                    if(this.userInfo.role !== 'ADMINISTRATOR') {
                        router.push("/")
                    }
                })

            axios.get("/administrator/get-free-managers", this.configHeaders)
                .then(response => {
                    this.managerList = response.data.freeManagers;
                })

        }
        const map = new ol.Map({
            target: 'map23',
            layers: [
                new ol.layer.Tile({
                    source: new ol.source.OSM()
                })
            ],
            view: new ol.View({
                center: ol.proj.fromLonLat([20.028906272453145,44.953121262634596]),
                zoom: 10
            })

        });
        map.on('click', function (evt) {

            var coords = ol.proj.toLonLat(evt.coordinate);
            document.getElementById("geoLength").value = coords[0]
            document.getElementById("geoWidth").value = coords[1]
        })


    },
});