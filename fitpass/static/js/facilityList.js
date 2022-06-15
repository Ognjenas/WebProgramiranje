Vue.component("facility-list", {
    data: function () {
        return {
           facilitiesDto: null
        }
    },
    template: ` 
<div>
	Available Facilities:
	<table>
	<tr>
	    <th></th>
		<th>Name</th>
		<th>Type</th>
		<th>Location</th>
		<th>Average Grade</th>
		<th>Working Hours</th>
	</tr>
		
	<tr v-for="f in facilitiesDto.allFacilities">
		<td></td>
		<td>{{f.name}}</td>
		<td>{{f.type}}</td>
		<td>
		    <p>Geo.Length:{{f.location.geoLength}} / Geo.Width:{{f.location.geoWidth}}</p>
		    <p>City:{{f.location.city}}-Street:{{f.location.street}}-Nr:{{f.location.strNumber}}-Postal:{{f.location.postalCode}}</p>
		</td>
		<td>{{f.averageGrade}}</td>
		<td>
		    <p>Monday-Friday: From {{f.openTime.startWorkingDays.hour}}:{{f.openTime.startWorkingDays.minute}} to {{f.openTime.endWorkingDays.hour}}:{{f.openTime.endWorkingDays.minute}}</p>
		    <p>Saturday: From {{f.openTime.startSaturday.hour}}:{{f.openTime.startSaturday.minute}} to {{f.openTime.endSaturday.hour}}:{{f.openTime.endSaturday.minute}}</p>
		    <p>Sunday: From {{f.openTime.startSunday.hour}}:{{f.openTime.startSunday.minute}} to {{f.openTime.endSunday.hour}}:{{f.openTime.endSunday.minute}}</p>
		</td>
	</tr>
</table>
	<p>
		<a href="#/cnt">Contact</a>
	</p>
</div>		  
`
    ,
    methods :
        {

    },

    mounted () {
        axios
            .get('/facilities/')
            .then(response => (this.facilitiesDto = response.data))
    },
});