using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BuildManager : MonoBehaviour {

	public TurretData laserTurretData;
	public TurretData missileTurretData;
	public TurretData standardTurretData;

	//Indicates the current turret to be built
	public TurretData selectedTurretData;

	public void OnLaserSelected(bool isOn)
	{
		if(isOn)
		{
		 selectedTurretData = laserTurretData;
		}

	}

	public void OnMissileSelected(bool isOn)
	{
		if(isOn)
		{
		 selectedTurretData = missileTurretData;
		}

	}

	public void OnStandardSelected(bool isOn)
	{
		if(isOn)
		{
		 selectedTurretData = standardTurretData;
		}
	}
}
