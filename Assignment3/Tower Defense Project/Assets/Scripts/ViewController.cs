using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ViewController : MonoBehaviour {

	public float speed = 19;
	public float mouseSpeed = 300;
	// Update is called once per frame
	void Update () {
		float h = Input.GetAxis("Horizontal");
		float v = Input.GetAxis("Vertical");
		float mouse = Input.GetAxis("Mouse ScrollWheel");
		transform.Translate (new Vector3 (v*speed, mouse*speed, h*speed) *Time.deltaTime*speed ,Space.World);
	}
}
