using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ViewController : MonoBehaviour {

	public float speed = 28;
	public float mouseSpeed = 300;
	// Update is called once per frame
	void Update () {
		float h = Input.GetAxis("Horizontal");
		float v = Input.GetAxis("Vertical");
		float mouse = Input.GetAxis("Mouse ScrollWheel");
		transform.Translate (new Vector3 (h*speed, mouse*speed, v*speed) *Time.deltaTime*speed ,Space.World);
	}
}
