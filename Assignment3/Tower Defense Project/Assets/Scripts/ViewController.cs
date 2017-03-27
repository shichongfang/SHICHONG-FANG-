using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ViewController : MonoBehaviour {

	public float speed = 28;
	
	// Update is called once per frame
	void Update () {
		float h = Input.GetAxis("Horizontal");
		float v = Input.GetAxis("Vertical");
		transform.Translate (new Vector3 (h, 0, v) *Time.deltaTime*speed ,Space.World);
	}
}
