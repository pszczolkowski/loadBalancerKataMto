package edu.iis.mto.serverloadbalancer;

import static edu.iis.mto.serverloadbalancer.CurrentLoadPercentageMatcher.hasLoadPercentageOf;
import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static edu.iis.mto.serverloadbalancer.VmBuilder.vm;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith( Parameterized.class )
public class ServerLoadBalancerAdditionalTest extends ServerLoadBalancerBaseTest{

	private Integer[] serverCapacities;
	private Integer[] vmSizes;
	private Integer[][] expectedVmsOnServers;
	private Double[] expectedLoads;
	
	public ServerLoadBalancerAdditionalTest(Integer[] serverCapacities,
			Integer[] vmSizes, Integer[][] expectedVmsOnServers, Double[] expectedLoads) {
		
		if( serverCapacities.length != expectedVmsOnServers.length )
			throw new IllegalArgumentException( "serverCapacities should have the same length as expectedVmsOnServers" );
		if( serverCapacities.length != expectedLoads.length )
			throw new IllegalArgumentException( "serverCapacities should have the same length as expectedLoads" );
		
		this.serverCapacities = serverCapacities;
		this.vmSizes = vmSizes;
		this.expectedVmsOnServers = expectedVmsOnServers;
		this.expectedLoads = expectedLoads;
	}

	@Parameters
	public static Collection< Object[] > getData(){
		return Arrays.asList( new Object[][] {
				{ 
					serverCapacitiesWith(4, 6) , 
					vmSizesWith( 1 , 4 , 2 ), 
					vmsOnServers( vmsOnServer(0,2) , vmsOnServer(1) ),
					loadsOf( 75.0 , 66.66 )
				},{
					serverCapacitiesWith( 6,5,3 ) , 
					vmSizesWith( 1,2,4,2 ) , 
					vmsOnServers( vmsOnServer(0,2) , vmsOnServer(1), vmsOnServer(3) ),
					loadsOf( 83.33 , 40.0 , 66.66 )
				}
		});
	}
	
	@Test 
	public void balance_serversAndVms() {
		// utworzenie tablicy serwerów z podanymi rozmiarami
		Server[] servers = new Server[ serverCapacities.length ];
		for (int i = 0 ; i < serverCapacities.length ; i++) {
			servers[ i ] = a( server().withCapacity( serverCapacities[ i ] ) );
		}
		
		// utworzenie tablicy maszyn z podanymi rozmiarami
		Vm[] vms = new Vm[ vmSizes.length ];
		for( int i = 0 ; i < vmSizes.length ; i++ ){
			vms[ i ] = a( vm().ofSize( vmSizes[ i ] ) );
		}
        
        balance( servers , vms );
        
        // sprawdzenie czy maszyny zosta³y w³aœciwie przypisane do serwerów
        for( int i = 0 ; i < expectedVmsOnServers.length ; i++ ){
        	for( int j = 0 ; j < expectedVmsOnServers[ i ].length ; j++ ){
        		assertThat( "The server " + i + " should contain the vm " + expectedVmsOnServers[ i ][ j ] , servers[ i ].contains( vms[ expectedVmsOnServers[ i ][ j ] ] ) );
        	}
        }
        
        // sprawdzenie czy serwery zosta³y za³adowane w odpowienim stopniu
        for( int i = 0 ; i < expectedLoads.length ; i++ ){
        	assertThat( servers[ i ] , hasLoadPercentageOf( expectedLoads[ i ] ) );
        }
	}
	
	
	private static Integer[] vmsOnServer(Integer... vms) {
		return vms;
	}

	private static Integer[][] vmsOnServers(Integer[]... vmsOnServers) {
		return vmsOnServers;
	}

	private static Double[] loadsOf( Double... loads ) {
		return loads;
	}

	private static Integer[] vmSizesWith( Integer... sizes ) {
		return sizes;
	}

	private static Integer[] serverCapacitiesWith( Integer... capacities ) {
		return capacities;
	}

}
