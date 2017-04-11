using System.Collections;
using System.Collections.Generic;
using UnityEngine;

//Save Every wave of Enemy's attribute needs 
[System.Serializable]
public class Wave {
	public GameObject enemyPrefab;
	public int count;
	public float rate;
}

