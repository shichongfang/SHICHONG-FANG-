using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.EventSystems;
using UnityEngine.UI;

public class BuildManager : MonoBehaviour {

	public TurretData laserTurretData;
	public TurretData missileTurretData;
	public TurretData standardTurretData;

	//表示当前选择的炮台(要建造的炮台)
	private TurretData selectedTurretData;

    private MapCube selectedMapCube;

	public Text moneyText;

	public Animator moneyAnimator;

	private int money = 1000;

	void ChangeMoney(int change=0)
	{
		money += change;
		moneyText.text = "$" + money;
	}
	//current selected turret

	void Update()
	{
		if ( Input.GetMouseButtonDown(0))
		{
			if (EventSystem.current.IsPointerOverGameObject()==false)
			{
				//build turret
				Ray ray = Camera.main.ScreenPointToRay(Input.mousePosition);
				RaycastHit hit;
				bool isCollider = Physics.Raycast(ray,out hit, 1000, LayerMask.GetMask("MapCube"));
				if (isCollider)
				{		
					MapCube mapCube = hit.collider.GetComponent<MapCube>();
					if (selectedTurretData != null && mapCube.turretGo == null)
					{
						//can create
						if(money > selectedTurretData.cost)
						{
							ChangeMoney(-selectedTurretData.cost);
							mapCube.BuildTurret(selectedTurretData.turretPrefab);
						}
						else
						{
							// not enough money
							moneyAnimator.SetTrigger("Flicker");
						}
					}
					else
					{
						//todo Update
					}

				}
			}
		}
	}

	public void OnLaserSelected(bool isOn)
	{
		if (isOn)
		{
			selectedTurretData = laserTurretData;
		}
	}

	public void OnMissileSelected(bool isOn)
	{
		if (isOn)
		{
			selectedTurretData = missileTurretData;
		}
	}
	public void OnStandardSelected(bool isOn)
	{
		if (isOn)
		{
			selectedTurretData = standardTurretData;
		}
	}
}
