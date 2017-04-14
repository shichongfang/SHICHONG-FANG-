using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MapCube : MonoBehaviour {
  [HideInInspector]
	public GameObject turretGo;//save curret turret on the cube

  public void BuildTurret(GameObject turretPrefab)
	{
		turretGo = GameObject.Instantiate(turretPrefab,transform.position,Quaternion.identity);
	}
}
