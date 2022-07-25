fetch("/Projekt_M133_Military_ranking-1.0/resource/vehicle/list")
    .then((response) => {
        if (response.status == 403) {
            document.querySelector("#vehicles").innerHTML = "";
            document.querySelector("#message").innerHTML =
                "You are not authorized to view this page.";
            throw new Error("Authorization failed");
        }
        return response.json();
    })
    .then((vehicles) => {
        const vehicleList = document.querySelector("#vehicles");
        vehicles.forEach((vehicle) => {
            console.log(vehicle)

            const row = document.createElement("tr");
            row.innerHTML = `
					<td>${vehicle.vehicleID}</td>
					<td>${vehicle.vehicleName}</td>
					<td>${vehicle.registrationDate}</td>
					<td>${vehicle.quantity}</td>
					<td>${vehicle.battlepoints}</td>
					<td>${vehicle.weaponIDs}</td>
				`;
            vehicleList.appendChild(row);
        });
        vehicleList.style.display = "table";
    });
