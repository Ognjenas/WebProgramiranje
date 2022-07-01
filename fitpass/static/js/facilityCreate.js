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
            },
            selectedFile: null,
            validation: {
                nameValid: false,
                typeValid: false,
                cityValid: false,
                streetValid: false,
                strNumValid: false,
                postCodeValid: false,
                geoWidth: false,
                geoLength: false,

            },
            isDisabled: true,
        }
    },
    template: ` 
<div>
    <h1>OVO JE FACILITY CREATE</h1>
    <form method="post">
    <table>
    <tr>
    <td><label>Name</label></td>
    <td><input id="name" name="name" v-model="form.name" v-on:input="validate()" placeholder="Enter Facility Name"></td>
    <td><label v-if="validation.nameValid">Please enter only Text</label></td>
    </tr>
    
    <tr>
    <td><label>Type</label></td>
    <td><select id="type" name="type" v-model="form.type" v-on:input="validate()" >
      <option value="">Select Facility Type</option>
      <option value="GYM">GYM</option>
      <option value="FIGHTING_GYM">FIGHTING_GYM</option>
      <option value="SWIMMING_POOL">SWIMMING_POOL</option>
      <option value="SPORTS_CENTER">SPORTS_CENTER</option>
      <option value="DANCE_STUDIO">DANCE_STUDIO</option>
      <option value="STADIUM">STADIUM</option>
    </select></td>
    <td><label v-if="validation.typeValid">Please select a Type</label></td>
    </tr>
    
    <tr>
    <td colspan="3"><label>LOCATION</label></td>
    </tr>
    
    <tr>
    <td><label>City</label></td>
    <td><input id="city" name="city" v-model="form.city" v-on:input="validate()" placeholder="Enter City Name"></td>
    <td><label v-if="validation.cityValid">Please enter only Text</label></td>
    </tr>
    
    <tr>
    <td><label>Street</label></td>
    <td><input id="street" name="street" v-model="form.street" v-on:input="validate()" placeholder="Enter Street Name"></td>
    <td><label v-if="validation.streetValid">Please enter only Text</label></td>
    </tr>
    
    <tr>
    <td><label>Street Number</label></td>
    <td><input id="strNum" name="strNum" v-model="form.strNum" v-on:input="validate()" placeholder="Enter Street Number"></td>
    <td><label v-if="validation.strNumValid">Please enter only whole Numbers</label></td>
    </tr>
    
    <tr>
    <td><label>Postal Code</label></td>
    <td><input id="postCode" name="postCode" v-model="form.postCode" v-on:input="validate()" placeholder="Enter Postal Code"></td>
    <td><label v-if="validation.postCodeValid">Please enter only whole Numbers</label></td>
    </tr>
    
    <tr>
    <td><label>Geographical Width</label></td>
    <td><input id="geoWidth" name="geoWidth" v-model="form.geoWidth" v-on:input="validate()" placeholder="Enter Geographical Width"></td>
    <td><label v-if="validation.geoWidthValid">Please enter only decimal Numbers</label></td>
    </tr>
    
    <tr>
    <td><label>Geographical Length</label></td>
    <td><input id="geoLength" name="geoLength" v-model="form.geoLength" v-on:input="validate()" placeholder="Enter Geographical Length"></td>
    <td><label v-if="validation.geoLengthValid">Please enter only decimal Numbers</label></td>
    </tr>
    
    <tr>
    <td><label>Select Image</label></td>
    <td colspan="2"><input type="file" v-on:change="fileSelected()" accept="image/*" ></td>
    </tr>
    
    <tr>
    <td><button type="button" name="createFacilityButton" v-on:click="createFacility()" :disabled="isDisabled" >CreateFacility</button></td>
    <td></td>
    <td><button type="reset" name="resetFormButton" v-on:click="resetForm()">Reset</button></td>
    </tr>
    </table>
    </form>
    
    <p>create-form:  |{{form.name}}|,|{{form.type}}|,|{{form.city}}|,|{{form.street}}| </p>
</div>		  
`
    ,
    methods: {
        createFacility() {

            axios.post('/facilities/create', this.form)
        },

        fileSelected: function (event) {
            this.selectedFile = event.target.files[0];
        },

        validate() {
/*            console.log(this.form);
            console.log(this.selectedFile);*/
            //this.validateFields();
            if (this.form.name === "" || this.form.type === "" || this.form.city === "" || this.form.street === ""
                || this.form.strNum === "" || this.form.postCode === "" || this.form.geoWidth === "" || this.form.geoLength === ""
            ) {
                this.isDisabled = true;
            } else {
                this.isDisabled = false;
            }
        },

        resetForm(){
            this.form.name="";
            this.form.type="";
            this.form.city="";
            this.form.street="";
            this.form.strNum="";
            this.form.postCode="";
            this.form.geoWidth="";
            this.form.geoLength="";
        },

    },

    mounted() {
        if ($cookies.get("token") == null) {
            router.push("/login")
        }
    },
});